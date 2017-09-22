#!/bin/sh


# Clean directory
rm -r build
mkdir build
mkdir build/production
mkdir build/production/data
mkdir build/artifacts


# Unzip dependency jars
tar -zxf /Users/ferdi/Documents/Processing/libraries/controlP5/library/controlP5.jar -C build/production/
tar -zxf /Applications/Processing.app/Contents/Java/core/library/core.jar -C build/production/
tar -zxf /Applications/Processing.app/Contents/Java/core/library/gluegen-rt-natives-macosx-universal.jar -C build/production/
tar -zxf /Applications/Processing.app/Contents/Java/core/library/gluegen-rt.jar -C build/production/
tar -zxf /Applications/Processing.app/Contents/Java/core/library/jogl-all-natives-macosx-universal.jar -C build/production/
tar -zxf /Applications/Processing.app/Contents/Java/core/library/jogl-all.jar -C build/production/
tar -zxf /Applications/Processing.app/Contents/Java/modes/java/libraries/serial/library/serial.jar -C build/production/
tar -zxf /Applications/Processing.app/Contents/Java/modes/java/libraries/serial/library/jssc.jar -C build/production/
tar -zxf /Applications/Processing.app/Contents/Java/modes/java/libraries/net/library/net.jar -C build/production/


# Copy assets
cp -r assets/data/ build/production/data/


# Copy manifest
cp -r src/META-INF/MANIFEST.MF build/production/META-INF/MANIFEST.MF


# Compile JAVA to classes
javac \
-sourcepath src \
-classpath "/Users/ferdi/Documents/Processing/libraries/controlP5/library/controlP5.jar:/Applications/Processing.app/Contents/Java/core/library/core.jar:/Applications/Processing.app/Contents/Java/core/library/gluegen-rt-natives-macosx-universal.jar:/Applications/Processing.app/Contents/Java/core/library/gluegen-rt.jar:/Applications/Processing.app/Contents/Java/core/library/jogl-all-natives-macosx-universal.jar:/Applications/Processing.app/Contents/Java/core/library/jogl-all.jar:/Applications/Processing.app/Contents/Java/modes/java/libraries/serial/library/serial.jar:/Applications/Processing.app/Contents/Java/modes/java/libraries/serial/library/jssc.jar:/Applications/Processing.app/Contents/Java/modes/java/libraries/net/library/net.jar" \
-d build/production/ \
src/com/madebyferdi/folume/Main.java


# Create JAR
jar \
-cvfm build/artifacts/Folume.jar \
build/production/META-INF/MANIFEST.MF \
-C build/production/ .

