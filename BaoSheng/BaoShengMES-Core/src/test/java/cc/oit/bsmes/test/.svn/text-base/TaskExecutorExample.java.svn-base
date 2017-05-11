package cc.oit.bsmes.test;

import org.springframework.core.task.TaskExecutor;  

public class TaskExecutorExample {  
    private TaskExecutor taskExecutor;  
    public TaskExecutorExample(TaskExecutor taskExecutor) {      
        this.taskExecutor = taskExecutor;    
    }  
    public void printMessages() {      
        for(int i = 0; i < 25; i++) {        
            taskExecutor.execute(new MessagePrinterTask("Message" + i));      
        }    
    }  
      
      
    private class MessagePrinterTask implements Runnable {      
        private String message;      
        public MessagePrinterTask(String message) {        
            this.message = message;      
        }      
        public void run() {        
            System.out.println(message);      
        }  
    }  
} 