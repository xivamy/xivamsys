/*package com.xiva.test.workflow;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import junit.framework.TestCase;

public class HelloWorldTest extends TestCase{

    public void testHelloWorldProcess() {
//        ProcessDefinition processDefinition = ProcessDefinition.parseXmlString(
//                "<process-definition>" +
//                "  <start-state>" +
//                "    <transition to='s' />" +
//                "  </start-state>" +
//                "  <state name='s'>" +
//                "    <transition to='end' />" +
//                "  </state>" +
//                "  <end-state name='end' />" +
//                "</process-definition>"
//              );
        ProcessDefinition processDefinition = ProcessDefinition.parseXmlResource("config/hello.xml");
        ProcessInstance processInstance = 
            new ProcessInstance(processDefinition);
        Token token = processInstance.getRootToken();

        assertSame(processDefinition.getStartState(), token.getNode());
        
        token.signal();
        
        assertSame(processDefinition.getNode("s"), token.getNode());
        
        token.signal();
        
        assertSame(processDefinition.getNode("end"), token.getNode());
    }
}
*/