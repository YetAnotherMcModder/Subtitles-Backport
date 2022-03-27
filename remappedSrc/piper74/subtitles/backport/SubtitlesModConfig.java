package piper74.subtitles.backport;

public class SubtitlesModConfig {
    public boolean enabled;
    public boolean transparentBackground;
    public boolean hideUnknown;

    public SubtitlesModConfig(boolean enabled, boolean transparentBackground, boolean hideUnknown) {
        this.enabled = enabled;
        this.transparentBackground = transparentBackground;
        this.hideUnknown = hideUnknown;
    }
}
