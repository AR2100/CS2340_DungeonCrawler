## 2340dungeon
A Repo for CS2340 Spring 2021 for Team 58.

## Dependencies
JavaSDK 11

JavaFX: https://gluonhq.com/products/javafx/

Download the JavaFX version depending on you operating system.

CheckStyle: download the CheckStyle from the CS 2340 Canvas. To get the .xml file that IntelliJ uses, run the python script in the command line; the script will download the .xml file.

## Configuration (IntelliJ IDEA)
1. Clone the project:
```
git clone https://github.gatech.edu/rchillara3/2340dungeon.git
```

2. In IntelliJ, go to File > Open > 2340dungeon.
3. Go to File > Project Structure .
    1. Under "Project", ensure that "Project SDK" is set to a version of jdk11.
    2. Click on "Libraries".
        1. If there is a "lib" entry that already exists, right click on it and delete it.
        2. Click on the plus sign at the top left labeled "New Project Library", and select "Java" in the dropdown menu.
        3. In the file explorer, find your JavaFX folder. Select the "lib" folder inside your JavaFX folder, and click OK.
    3. Click on "Apply" to apply the changes. Then, click on OK.
        1. After this step, all of the errors about IntelliJ not recognizing JavaFX libraries should disappear.
4. Build the project by clicking on the green hammer in the top menu bar. The project should build successfully.
5. In the Project file explorer, go to 2340dungeon > src > sample > Main. Right click on Main.java and select "Run Main.java". IntelliJ should throw an error, 'Error: JavaFX runtime components are missing, and are required to run this application'.
6. Now, in the top menu bar, select Run > Edit Configurations.
    1. Under "Applications" on the left side, make sure "Main" is selected.
    2. On the right side of the "Build and Run" menu, you should see a "Modify options" dropdown. Click on "Modify options" and select "Add VM Options". A new textbox should appear that says "VM options" when you hover over it.
    3. Paste the following command into the "VM options" text box:
    ```
    --module-path /path/to/javafx-sdk-11/lib --add-modules javafx.controls,javafx.fxml
    ```
    Replace  `/path/to/javafx-sdk-11/lib` with the path to the lib file in your JavaFX folder.
    4. Click "Apply" to apply the changes. Then, click on OK.
7. Build the project again. This time, the project will build successfully, and the JavaFX window should appear.

## TestFX Configuration (IntelliJ)
1. With 2340dungeon pulled up, click on File > Project Structure > Libraries.
   1. Click on the plus at the top left labeled "New Project Library", then click on "Maven" in the dropdown menu.
   2. In the text box, copy the follow text: `org.testfx:testfx-core:4.0.15-alpha`. Then press OK.
   3. Repeat steps (a) and (b), but instead copy `org.testfx:testfx-junit:4.0.15-alpha`.
2. To run a TestFX test, navigate to the "tests" folder and right click on the test (in this case, the TestFX test case is ControllerTest) and hit "run test".

## CheckStyle Configuration (IntelliJ)

1. If you don't have Checkstyle installed, go to File > Settings > Plugins. 
    1. Type in "Checkstyle". Then, install Checkstyle.
    2. After Checkstyle installs, click "Apply" and then OK. 
    3. Restart IntelliJ.
2. Now, go to File > Settings > Tools > Checkstyle.
    1. Set the Checkstyle version to 8.24.
    2. Click the plus at the bottom left-hand corner of the "Configuration File" Box. Click on "Use a local Checkstyle File". Give the config file a name.
    3. Click "Browse" and locate the "cs2340_checks.xml" file inside 2340dungeon > checkstyle.
    4. Hit next, and then OK.
3. To run checkstyle, click on the "CheckStyle" tab on the bottom bar of your screen.
    1. In the "Rules" dropdown, select the cs2340 CheckStyle file.
    2. Click on the green play button to run CheckStyle.
4. REMEMBER: Before turning in a milestone, run the "run checkstyle.py" in the "2340dungeon" directory in the command line! The graders will use this, so be sure there are no errors!  
