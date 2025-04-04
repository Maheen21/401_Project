@echo off
setlocal EnableDelayedExpansion

echo === Dishcraft Application Startup Benchmark ===
echo.

set RUNS=3

echo Comparing JAR vs Native Image startup times (%RUNS% runs each)
echo.

:: JAR Startup Time
if exist target\dishcraft-0.0.1-SNAPSHOT.jar (
    echo Measuring JAR startup time...
    for /L %%i in (1,1,%RUNS%) do (
        echo   Run %%i/%RUNS%...
        
        :: Capture start time
        set start=!time!
        
        :: Start the application with random port to avoid conflicts and update mode
        start /b "" java -jar target\dishcraft-0.0.1-SNAPSHOT.jar --server.port=0 --spring.jpa.hibernate.ddl-auto=update --spring.datasource.initialization-mode=never --spring.sql.init.mode=never > jar_output.txt
        
        :: Wait for startup message
        :jar_wait_loop
        findstr /C:"Started DishcraftApplication" jar_output.txt > nul
        if errorlevel 1 (
            timeout /t 1 > nul
            goto jar_wait_loop
        )
        
        :: Capture end time and extract startup time from log
        set end=!time!
        
        :: Kill the process
        for /f "tokens=5" %%p in ('netstat -aon ^| findstr "LISTENING" ^| findstr "java"') do (
            taskkill /F /PID %%p > nul 2>&1
        )
        
        :: Extract reported time from output
        for /f "tokens=7" %%t in ('findstr /C:"Started DishcraftApplication in" jar_output.txt') do (
            echo     Reported startup time: %%t seconds
        )
        
        :: Wait to ensure full shutdown
        timeout /t 2 > nul
    )
    echo.
) else (
    echo JAR file not found! Build it with: mvnw clean package
    echo.
)

:: Native Image Startup Time
if exist target\dishcraft.exe (
    echo Measuring Native Image startup time...
    for /L %%i in (1,1,%RUNS%) do (
        echo   Run %%i/%RUNS%...
        
        :: Capture start time
        set start=!time!
        
        :: Start the application with random port to avoid conflicts and update mode
        start /b "" target\dishcraft.exe --server.port=0 --spring.jpa.hibernate.ddl-auto=update --spring.datasource.initialization-mode=never --spring.sql.init.mode=never > native_output.txt
        
        :: Wait for startup message
        :native_wait_loop
        findstr /C:"Started DishcraftApplication" native_output.txt > nul
        if errorlevel 1 (
            timeout /t 1 > nul
            goto native_wait_loop
        )
        
        :: Capture end time
        set end=!time!
        
        :: Kill the process
        for /f "tokens=5" %%p in ('netstat -aon ^| findstr "LISTENING" ^| findstr "dishcraft.exe"') do (
            taskkill /F /PID %%p > nul 2>&1
        )
        
        :: Extract reported time from output
        for /f "tokens=7" %%t in ('findstr /C:"Started DishcraftApplication in" native_output.txt') do (
            echo     Reported startup time: %%t seconds
        )
        
        :: Wait to ensure full shutdown
        timeout /t 2 > nul
    )
    echo.
) else (
    echo Native executable not found! Build it with: mvnw -Pnative clean package
    echo.
)

echo Benchmark complete!
del jar_output.txt 2>nul
del native_output.txt 2>nul