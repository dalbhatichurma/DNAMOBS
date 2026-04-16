@echo off
echo Building DNAMobs...
echo.

mvn clean package

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Build successful!
    echo JAR file location: target\DNAMobs-1.0.0.jar
    echo ========================================
) else (
    echo.
    echo ========================================
    echo Build failed! Check the errors above.
    echo ========================================
)

pause
