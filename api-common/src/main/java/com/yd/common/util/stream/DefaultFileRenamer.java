package com.yd.common.util.stream;

import java.io.File;

public class DefaultFileRenamer extends FileRenamer {
    public DefaultFileRenamer() {
    }

    @Override
    public File rename(File f) {
        if (this.createNewFile(f)) {
            return f;
        } else {
            String name = f.getName();
            String body = null;
            String ext = null;
            int dot = name.lastIndexOf(".");
            if (dot != -1) {
                body = name.substring(0, dot);
                ext = name.substring(dot);
            } else {
                body = name;
                ext = "";
            }

            String newName;
            for(int count = 0; !this.createNewFile(f) && count < 9999; f = new File(f.getParent(), newName)) {
                ++count;
                newName = body + count + ext;
            }

            return f;
        }
    }
}