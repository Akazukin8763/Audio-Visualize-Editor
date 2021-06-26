package com.visualize;

import java.util.HashMap;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;
import com.sun.jna.win32.W32APIOptions;
import com.sun.jna.platform.win32.WinDef;

public class Wallpaper {

    private static WinDef.RECT rcWorkArea = new WinDef.RECT();

    private interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

        WinDef.HWND FindWindow(String lpClassName, String lpWindowName);

        boolean GetWindowRect(WinDef.HWND hwnd, WinDef.RECT rect);
    }

    private interface SPI extends StdCallLibrary {
        // https://docs.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-systemparametersinfoa
        int SPI_SETDESKWALLPAPER = 0x0014;
        int SPI_GETWORKAREA = 0x0030;

        int SPIF_UPDATEINIFILE = 0x01;
        int SPIF_SENDWININICHANGE = 0x02;

        SPI INSTANCE = (SPI) Native.loadLibrary( "user32", SPI.class, new HashMap() {
            {
                put(OPTION_TYPE_MAPPER, W32APITypeMapper.UNICODE);
                put(OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.UNICODE);
            }
        });

        boolean SystemParametersInfo(long uiAction, int uiParam, WinDef.RECT pvParam, long fWinIni);
    }

    public static WinDef.RECT getRect(String windowName) throws WindowNotFoundException, GetWindowRectException {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, windowName);
        WinDef.RECT rect = new WinDef.RECT();

        if (hwnd == null)
            throw new WindowNotFoundException("", windowName);

        if (!User32.INSTANCE.GetWindowRect(hwnd, rect))
            throw new GetWindowRectException(windowName);

        return rect;
    }

    @SuppressWarnings("serial")
    public static class WindowNotFoundException extends Exception {
        public WindowNotFoundException(String className, String windowName) {
            super(String.format("Window null for className: %s; windowName: %s",
                    className, windowName));
        }
    }

    @SuppressWarnings("serial")
    public static class GetWindowRectException extends Exception {
        public GetWindowRectException(String windowName) {
            super("Window Rect not found for " + windowName);
        }
    }

    public static void main(String[] args) {
        String windowName = "工作管理員";

        try {
            rcWorkArea = Wallpaper.getRect(windowName);
            System.out.printf("The corner locations for the window \"%s\" are %s\n", windowName, rcWorkArea);
        } catch (WindowNotFoundException | GetWindowRectException e) {
            e.printStackTrace();
        }

        rcWorkArea = Wallpaper.getWorkArea();
        System.out.printf("The corner locations for the window \"Work Area\" are %s\n", rcWorkArea);
    }

    public static WinDef.RECT getWorkArea() {
        WinDef.RECT rect = new WinDef.RECT();

        SPI.INSTANCE.SystemParametersInfo (
                SPI.SPI_GETWORKAREA
                , 0
                , rect
                , 0
        );

        return rect;
    }

    /*
    public static void putImage(String path) {
        SPI.INSTANCE.SystemParametersInfo (
                SPI.SPI_SETDESKWALLPAPER
                , 0
                , path
                , SPI.SPIF_UPDATEINIFILE | SPI.SPIF_SENDWININICHANGE
        );
    }
    */
}
