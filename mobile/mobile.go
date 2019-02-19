package mobile

import (
        "github.com/hajimehoshi/ebiten/mobile"
        "github.com/FriendlyUser/flappyPlane"
)

var (
        running bool
        ScreenHeight int 
        ScreenWidth  int
)

// IsRunning returns a boolean value indicating whether the game is running.
func IsRunning() bool {
        return running
}

func getWidth() int {
        return ScreenWidth
}

func getHeight() int {
        return ScreenHeight
}
// Start starts the game.
func Start(scale float64, Width int, Height int) error {
        running = true
        game := flappyPlane.NewGame()
        ScreenHeight = Height 
        ScreenWidth = Width
        // mobile.Start starts the game.
        // In this function, scale is passed from Java/Objecitve-C side
        // and pass it to mobile.Start. You can also receive the screen
        // size from Java/Objetive-C side by adding arguments to this
        // function if you want to make Java/Objective-C side decide the
        // screen size.
        // Note that the screen size unit is dp (device-independent pixel)
        // on Android.
        if err := mobile.Start(game.Update, Width, Height, scale, "Flappy Plane"); err != nil {
                return err
        }
        return nil
}

// Update proceeds the game.
func Update() error {
        return mobile.Update()
}

// UpdateTouchesOnAndroid dispatches touch events on Android.
func UpdateTouchesOnAndroid(action int, id int, x, y int) {
        mobile.UpdateTouchesOnAndroid(action, id, x, y)
}

// UpdateTouchesOnIOS dispatches touch events on iOS.
func UpdateTouchesOnIOS(phase int, ptr int64, x, y int) {
        // Prepare this function if you also want to make your game run on iOS.
        mobile.UpdateTouchesOnIOS(phase, ptr, x, y)
}