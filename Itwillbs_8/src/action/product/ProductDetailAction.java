package action.product;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.product.ProdReviewListService;
import svc.product.ProductDetailSelectService;
import vo.ActionForward;
import vo.PageInfo;
import vo.ProdReviewBean;
import vo.ProductBean;

public class ProductDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		ArrayList<ProductBean> productDetailList = new ArrayList<ProductBean>();
		ProductDetailSelectService pdss = new ProductDetailSelectService();
		String basicCode = request.getParameter("basicCode");
		
		// ----------------------상품 리뷰 가져오는 작업-------------------------
		// BIN
		int page=1;
		int limit = 50;
		if(request.getParameter("page")!=null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		ProdReviewListService prodReviewListService = new ProdReviewListService();
		int listCount = prodReviewListService.getReviewListCount(basicCode);
		ArrayList<ProdReviewBean> reviewList = new ArrayList<ProdReviewBean>();
		reviewList = prodReviewListService.getReviewList(page, limit, basicCode);
		// 페이지 계산 작업
		int maxPage = (int)((double)listCount/limit+ 0.95);
		int startPage = ((int)((double)page/10 +0.9)-1) *10 +1 ;
		int endPage = startPage +10 -1;
		if(endPage>maxPage) {
			endPage = maxPage;
		}
		PageInfo pageInfo = new PageInfo(page, maxPage, startPage, endPage, listCount);
		// ----------------------상품 리뷰 가져오는 작업-------------------------
		
		
		productDetailList = pdss.getProductDetailList(basicCode);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("reviewList", reviewList);
		request.setAttribute("productDetailList", productDetailList);
		
		forward = new ActionForward();
		forward.setPath("/product/product_detail.jsp");
		
		return forward;
	}

}
