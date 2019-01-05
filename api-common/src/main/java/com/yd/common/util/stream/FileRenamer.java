package com.yd.common.util.stream;

import java.io.File;
import java.io.IOException;

import com.yd.httpClient.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FileRenamer {
    private static final Logger logger = LoggerFactory.getLogger(FileRenamer.class);
    public static final FileRenamer RENAMER;

    public FileRenamer() {
    }

    public abstract File rename(File var1);

    protected boolean createNewFile(File f) {
        try {
            return f.createNewFile();
        } catch (IOException var3) {
            return false;
        }
    }

    static {
        FileRenamer fileRenamer = null;
        if (Constant.fileRenamer == null) {
            fileRenamer = new DefaultFileRenamer();
        } else {
            try {
                Class renameClass = Class.forName(Constant.fileRenamer);
                fileRenamer = (FileRenamer)renameClass.newInstance();
            } catch (ClassNotFoundException var2) {
                logger.error("Could not found FileRenamer Class." + var2.toString());
            } catch (InstantiationException var3) {
                logger.error("Could not init FileRenamer Class." + var3.toString());
            } catch (IllegalAccessException var4) {
                logger.error("Could not access FileRenamer Class." + var4.toString());
            }
        }

        RENAMER = (FileRenamer)fileRenamer;
    }
}