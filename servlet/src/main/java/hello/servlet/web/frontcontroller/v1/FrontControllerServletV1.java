package hello.servlet.web.frontcontroller.v1;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1" , urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {
    private Map<String,ControllerV1> controllerV1Map = new HashMap<>();
    public FrontControllerServletV1() {
        controllerV1Map.put("/front-controller/v1/members/new-form",new MemberFormControllerV1());
        controllerV1Map.put("/front-controller/v1/members/save",new MemberSaveControllerV1());
        controllerV1Map.put("/front-controller/v1/members",new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI();
        ControllerV1 controllerV1 = controllerV1Map.get(requestURI);

        if (controllerV1 == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        else{
            controllerV1.process(request,response);
        }
    }
}
