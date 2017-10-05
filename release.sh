#!/bin/sh


# Clean directory
rm -rf build
mkdir build
mkdir build/production
mkdir build/production/data
mkdir build/artifacts
mkdir build/deploy


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
cp -r assets/icon/icon-16.png build/production/icon/
cp -r assets/icon/icon-32.png build/production/icon/
cp -r assets/icon/icon-48.png build/production/icon/
cp -r assets/icon/icon-64.png build/production/icon/
cp -r assets/icon/icon-128.png build/production/icon/
cp -r assets/icon/icon-256.png build/production/icon/
cp -r assets/icon/icon-512.png build/production/icon/
cp -r assets/icon/icon-1024.png build/production/icon/
cp -r assets/icon/icon.icns build/production/icon/


# Copy manifest
cp -r src/META-INF/MANIFEST.MF build/production/META-INF/MANIFEST.MF


# Compile JAVA to classes
javac -sourcepath src -classpath "/Users/ferdi/Documents/Processing/libraries/controlP5/library/controlP5.jar:/Applications/Processing.app/Contents/Java/core/library/core.jar:/Applications/Processing.app/Contents/Java/core/library/gluegen-rt-natives-macosx-universal.jar:/Applications/Processing.app/Contents/Java/core/library/gluegen-rt.jar:/Applications/Processing.app/Contents/Java/core/library/jogl-all-natives-macosx-universal.jar:/Applications/Processing.app/Contents/Java/core/library/jogl-all.jar:/Applications/Processing.app/Contents/Java/modes/java/libraries/serial/library/serial.jar:/Applications/Processing.app/Contents/Java/modes/java/libraries/serial/library/jssc.jar:/Applications/Processing.app/Contents/Java/modes/java/libraries/net/library/net.jar" -d build/production/ src/com/madebyferdi/folume/Main.java


# Create JAR
jar -cvfm build/artifacts/Folume.jar build/production/META-INF/MANIFEST.MF -C build/production/ .


# Make an executable JAR file
javapackager -createjar -appclass com.madebyferdi.folume.Main -srcdir build/artifacts/ -srcfiles Folume.jar -outdir build/artifacts/ -outfile Folume.jar


# Codesign JAR
# jarsigner -storetype pkcs12 -keystore keystore.pfx build/artifacts/Folume.jar "Ferdi Koomen"
# jarsigner -verify build/artifacts/Folume.jar


# Package the JAR and JAVA runtime as a native application with installer
javapackager -deploy -appclass com.madebyferdi.folume.Main -srcdir build/artifacts/ -srcfiles Folume.jar -outdir build/deploy -outfile Folume -native image -name Folume -Bruntime="/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre" -Bicon="build/production/icon/icon.icns"


# Codesign application
codesign --force --deep --verbose --sign "Developer ID Application: XXXX" build/deploy/bundles/Folume.app
codesign --verbose --verify build/deploy/bundles/Folume.app


# Build a DMG file
dmgcanvas installer/installer.dmgCanvas build/deploy/bundles/Folume.dmg


# Code sign DMG file
codesign --verbose --sign "Developer ID Application: XXXX" build/deploy/bundles/Folume.dmg
codesign --verbose --verify build/deploy/bundles/Folume.dmg
