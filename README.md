# Reactive-Scriptifier
A companion app to Reactive Engine (https://github.com/liamroddy/Reactive-Engine). Scriptifier converts your handwritten game scripts into Reactive Engine-compatible JSX.

How it works:
1) Scriptifier comes with a handwritten sample script, called "inputScript.txt", the same script you can see up and running at http://danu6.it.nuigalway.ie/react/.
2) Run Scriptifier and select this sample file, or your own script.
3) Scriptifier will convert the file to JSX, creating a file called "game_logic.js" in the same directory.
4) Replace the pre-existing "game_logic.js" file in the React Engine's root directory.

Your game script should now be a playable adventure game.
