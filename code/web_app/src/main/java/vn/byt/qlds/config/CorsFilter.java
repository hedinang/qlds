package vn.byt.qlds.config;

import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.service.UnitCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
    @Autowired
    UnitCategoryService unitCategoryService;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        String dataBase = request.getHeader("data-base");

        String nameProvince = unitCategoryService.getProvinceName(dataBase);
        if (dataBase != null && nameProvince != null) {
            String session = StringUtils.convertNameProvinceToDbName(nameProvince);
            System.out.println("đang truy cập database "+ session);
        }

        if (!request.getMethod().equals("OPTIONS")) {
            chain.doFilter(request, res);
        } else {

        }
    }

    public void init(FilterConfig filterConfig) {

    }

    public void destroy() {

    }

}
