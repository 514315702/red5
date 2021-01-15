package org.red5.onlinelive.conf;

import org.red5.server.api.scope.IScope;
import org.red5.server.api.stream.IStreamFilenameGenerator;

public class CustomFilenameGenerator implements IStreamFilenameGenerator {

    /** Path that will store recorded videos */
    public String recordPath = "recordedStreams/";

    /** Path that contains VOD files */
    public String playbackPath = "videoStreams/";

    /** Set if the path is absolute or relative */
    public boolean resolvesAbsolutePath = false;


    @Override
    public String generateFilename(IScope scope, String name, GenerationType type) {
        return generateFilename(scope, name, null, type);
    }

    @Override
    public String generateFilename(IScope scope, String name, String extension, GenerationType type) {

        String filename;
        if (type == GenerationType.RECORD) {
            filename = recordPath + name;
        } else {
            filename = playbackPath + name;
        }
        if (extension != null) {
            // add the extension
            filename += extension;
        }
        System.out.println(filename);
        return filename;
    }

    @Override
    public boolean resolvesToAbsolutePath() {
        return resolvesAbsolutePath;
    }

    public void setRecordPath(String path) {
        recordPath = path;
    }

    public void setPlaybackPath(String path) {
        playbackPath = path;
    }

    public void setAbsolutePath(boolean absolute) {
        resolvesAbsolutePath = absolute;
    }
}
