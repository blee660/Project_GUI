# Project_GUI

##Requirements
You will require the following conditions to run this program:
- JVM installed on the machine
- An internet connection (for Bibliography Extraction)

##For End Users
This application works by extracting and converting information from PDF documents that are added into the "library" of documents on the left.
Documents can be added by clicking the "+" button located on the bottom left corner.

This application provides four functionalities for working with PDF documents. Each of the tools will begin execution when PDF documents are added into the library of documents of the application. The four tools in the application are as follows:
- Metadata Creation
- Keyphrase Extraction
- HTML Conversion
- Bibliography Extraction

The extracted information of the documents can be viewed on the main screen of the application by clicking on a document in the library.
Other options, such as viewing the original PDF document, the HTML file, and the extracted bibiliography, can be accessed by clicking the options menu located at the end of each item in the library.

The output from the execution of these tools are stored into a file in a directory named GUI700Home on the users home directory. A new session directory is created in GUI700Home every time the application is launched. All the generated output files from the session are placed into this session directory in its corresponding folders (i.e. metadata file stored into "metadata" folder, extracted keyphrases stored in "keyword" folder)

##For Developers
This application has been built with JavaFX, and functions by executing the four tools on PDF documents as stated above.
These tools can be downloaded, modified, and repackaged into a runnable jar with the same names as the original jar to be used in the application.
More detailed descriptions of the tool can be found in the README files of each tool

The source code is explained below:

When documents are added into the application, each of the four tools are executed concurrently on separate threads to improve the efficiency. The implementation of 

###Package - application
Responsible for controlling the user interface and handling of actions.
Contains fxml and css classes to contruct and style the GUI application.

**Main class:**
- entry point to the program
- creates GUI700Home directory in user's home directory
- creates session directory in GUI700Home
- launches application GUI

**MainController class:**
- contains methods to handle GUI components

**MetadataTab class:**
- updates main tab window for displaying the extracted information

**MyListItem class:**
- represents a document item in the library
- contains methods to update item fields and actions performed on the items

**PDFListController class:**
- handles addition and removal of documents from the library

###Package - pdfClasses
Contains representations of PDF list, PDF documents, and thesis metadata

**Library class:**
- manages worker threads to be executed on documents within the library
- ensures tasks are executed in order of dependency

**MetadataStorer class:**
- representation of thesis metadata
- getter and setter methods for retrieving and changing metadata information

**PDF class:**
- representation of a PDF document item
- contains methods to get and set document information
- updates the title of the document item in the library once title information has been extracted

###Package - resources
Contains the runnable jar files that carry out each of the four functionalities. These jar files can be modified and replaced, but it must be saved into the resources directory with the same name as the original jar file to conserve references to the tools. Once the jar file is replaced, refresh the project. More detailed descriptions of the tools are available in the README files and also the commenting within the source files. 

###Package - workerThreads
Classes that execute the jar files within the resources package
Tasks are separated into pre-execution task, main task and reliant task. Pre-execution tasks include tasks such as creating output directories. Main task includes the execution of the tool, and reliant tasks are executed after dependent tasks have finished (e.g. keyphrase extraction relies on metadata tool to extract abstract content of PDF document).


