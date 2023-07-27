package com.qst.extensions.huaweicloudtasks.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

public class TaskListWindowFactory  implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        TaskListWindow taskListWindow = new TaskListWindow(project, toolWindow);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(taskListWindow.getContentPanel(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
