
package com.mycompany.firstplugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name="startprogram")
public class Plugin extends AbstractMojo{
    
    @Parameter(property = "className")
    private String className;

    @Parameter(property = "path")
    private String path;
    
    @Parameter(property = "pathToRoot")
    private String pathToRoot;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (path != null || className != null ||  pathToRoot!=null) {
            Process p;
            System.out.println(" java -cp "+pathToRoot+" "+path+"."+className);
            try {
                p = Runtime.getRuntime().exec(new String[]{
                    "java",
                    "-cp",
                    pathToRoot,
                    path+"."+className    
                });

                BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = buf.readLine()) != null) System.out.println("[Output] "  + line);
                
                BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                while ((line = err.readLine()) != null) System.out.println("[Err] "  + line);
                
                p.waitFor();
            } catch (IOException ex) {
                Logger.getLogger(Plugin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Plugin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("Parameter don't exsists");
        }
    }
    
}
