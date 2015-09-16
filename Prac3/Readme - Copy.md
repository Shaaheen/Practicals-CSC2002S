All main files (java and XML) can be found in the app folder.
I have created shortcuts to the directory's of the important files:
	Java files : "java files - Shortcut"
	XML files : "XML files - Shortcut"
	
Specifically the java files are in :
	Practicals-CSC2002S\Prac3\app\src\main\java\me\shaaheen\prac3

And XML files are in:
	Practicals-CSC2002S\Prac3\app\src\main\res\layout
	
Extra credit:
	Images are saved onto the SD card/Device storage and then 
	are read from the device storage/SD card.
	
	-After much chat room debates about this, the answer I took
	 was that the images in the app should be read from the 
	 Device Storage/SD card hence I save all images from drawables
	 into the Device Storage/SD card under the folder "DiscoverUCT".
	 After this I attempt to read those same images in the Device 
	 Storage/SD card in the slide show
	 
	-Note that the emulators do not simulate device storage hence on
	 an emulator, my app will just display the images from drawable (as 
	 device storage won't be accessible) so as to prevent failing.

Just to mention a bit more details on the app
Note* The "Start Slideshow" button changes into "Stop Slideshow"
	  hence there are actually 4 buttons implemented
	  
Design:
	Jellybean API 14 was used as the min SDK as this would reach
	94% of android users while still allowing for good android 
	capabilities.
	This was done to consider that the users of the app might not
	have the latest technologies and hence latest android versions.
	Each image has a description to convey a message to users.
	
Functionality:
	-4 Functional buttons 
	- Start Slideshow changes into Stop slideshow (2 Buttons)
	- Next and Previous buttons
	- Back Button
	-Can also Swipe to navigate through slideshow
	
Following Conventions:
	Life cycles were considered with:
		- When SlideShow is running and the user moves/switches 
		  away from app, the slideshow pauses at that position(image) 
		  in the slideshow.
		- When user moves/switches from app, then when user goes back
		  into the app, the user will be at the same image he left
		  the app at.
		- Lifecycles were managed to keep app running smoothly
		
	Multi-threading:
		-At the welcome page, If images don't already exist on
		 device storage then a parallel asynchronous task is 
		 launched and saves all images in drawable into the 
		 device storage. All of this is done in parallel
		-Images are read from internal storage