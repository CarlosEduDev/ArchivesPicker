package com.ceasdev.archivespicker.data.works.abstraction;

import com.ceasdev.archivespicker.data.models.Archive;
import java.util.List;

public interface OnListArchivesListener{
    void onListArchivesPrepare();
    void onListArchivesProgress(int progress, int progressMax)
    void onListArchivesComplete(List<Archive> listArchives)
    void onListArchivesFailure(Exception exception);
}
