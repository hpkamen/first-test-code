<?xml version="1.0" encoding="utf-8"?>
<proguard-rules>
    # Keep AndroidX classes
    -keep class androidx.** { *; }
    -keep interface androidx.** { *; }
    
    # Keep our app classes
    -keep class com.example.gomoku.** { *; }
    
    # Keep Android system classes
    -keep class android.** { *; }
    -keep interface android.** { *; }
</proguard-rules>
