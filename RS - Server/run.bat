@echo off
@title [%date%] RuneSource

set host=127.0.0.1
set port=43594
set cyclerate=600

java -cp bin Server %host% %port% %cyclerate%
pause