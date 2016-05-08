package tools.docs;

import com.google.common.collect.ImmutableSet;
import tools.commands.CommandPageCreater;
import tools.hashmethods.HashAlgorithmsDescriptionTask;
import tools.permissions.PermissionsListWriter;
import tools.utils.ToolTask;

import java.util.Scanner;
import java.util.Set;

/**
 * Task that runs all tasks which update files in the docs folder.
 */
public class UpdateDocsTask implements ToolTask {

    private final Set<Class<? extends ToolTask>> TASKS = ImmutableSet.<Class<? extends ToolTask>>of(
        CommandPageCreater.class, HashAlgorithmsDescriptionTask.class, PermissionsListWriter.class);

    @Override
    public String getTaskName() {
        return "updateDocs";
    }

    @Override
    public void execute(Scanner scanner) {
        for (Class<? extends ToolTask> taskClass : TASKS) {
            try {
                ToolTask task = instantiateTask(taskClass);
                System.out.println("\nRunning " + task.getTaskName() + "\n-------------------");
                task.execute(scanner);
            } catch (UnsupportedOperationException e) {
                System.err.println("Error running task of class '" + taskClass + "'");
                e.printStackTrace();
            }
        }
    }

    private static ToolTask instantiateTask(Class<? extends ToolTask> clazz) {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
