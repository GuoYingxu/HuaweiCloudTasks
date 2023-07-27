package com.qst.extensions.huaweicloudtasks;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import com.qst.extensions.huaweicloudtasks.data.DataUtil;

public class ShowHwTasks extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        DataUtil.getProjectsBySdk();
//        Project project = e.getData(PlatformDataKeys.PROJECT);
//
//        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
//        String classPath = psiFile.getVirtualFile().getPath();
//        String title = "Hello World!";
//
//        Messages.showMessageDialog(project, classPath, title, Messages.getInformationIcon());
    }
}
