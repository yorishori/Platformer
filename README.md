# Platformer Game
2D Java platform game from scratch. Well, almost from
scratch because I'm using Java, and hardware-level 
libraries are hard to come by with and interpreted language.

### Features built so far:
**Window:**
* Using GLFW for callbacks, scene and window management.
* Loop using time differentials.
* GL port to draw and update screen.

**Scenes:**
* Game object initialization
* Frame and update management.
* View projections and camera management.
* Visual object control and shader initialization.

**Listeners:**
* Mouse Listener
* Key Listener

**Renderes**
* Vertex and Fragment shader building, compiling and linking.
* Texture upload from image.
* Custom shader program (simple though).

**Game Object and Components:**
* Following the Data Oriented Design.
* Creation of abstract game objects that contain a number of abstract components.
* For generalization purposes.