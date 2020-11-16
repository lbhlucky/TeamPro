<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../header.jsp" />

<!-- Cart -->
<jsp:include page="../sub_cart.jsp" />

<!-- breadcrumb -->
<div class="container">
	<div class="bread-crumb flex-w p-l-25 p-r-15 p-t-30 p-lr-0-lg">
		<a href="index.html" class="stext-109 cl8 hov-cl1 trans-04"> Home
			<i class="fa fa-angle-right m-l-9 m-r-10" aria-hidden="true"></i>
		</a> <span class="stext-109 cl4"> Shoping Cart </span>
	</div>
</div>

<link type="text/css" rel="stylesheet" href="../scss/common.css" />
<link type="text/css" rel="stylesheet" href="../scss/mp_order.css" />
<link type="text/css" rel="stylesheet" href="../scss/header.1.css" />
<link type="text/css" rel="stylesheet" href="../scss/menu.2.css" />
<!-- 주문상세 시작 -->


<div id="contentWrapper">
	<div id="contentWrap">

		<link type="text/css" rel="stylesheet"
			href="/shopimages/nasign/template/work/33865/menu.2.css?t=202005111439">
		<div id="aside">
			<h2 class="aside-tit">MY PAGE</h2>
			<div class="lnb-wrap">
				<div class="lnb-bx">
					<h2 class="txt txt1">SHOPPING INFO</h2>
					<div class="lnb">
						<ul>
							<li class="first"><a
								href="/shop/mypage.html?mypage_type=myorder">주문내역</a></li>
							<li><a href="/shop/mypage.html?mypage_type=mycoupon">쿠폰내역</a></li>
							<li><a href="/shop/mypage.html?mypage_type=myreserve">적립금내역</a></li>
							<li><a href="/shop/todaygoods.html">오늘본상품</a></li>
							<li><a href="/shop/mypage.html?mypage_type=mywishlist">상품
									보관함</a></li>
							<!-- <li class="attandance"><a href=""><strong>출석체크</strong></a></li>-->
						</ul>
					</div>
				</div>
				<div class="lnb-bx">
					<h2 class="txt txt2">SHOPPING QUESTION</h2>
					<div class="lnb">
						<ul>
							<li class="first"><a
								href="/shop/mypage.html?mypage_type=myarticle">내 게시글 보기</a></li>
							<li><a href="/shop/mypage.html?mypage_type=myemail">E-mail
									문의</a></li>
						</ul>
					</div>
				</div>
				<div class="lnb-bx">
					<h2 class="txt txt3">CUSTOMER INFO</h2>
					<div class="lnb">
						<ul>
							<li class="first"><a href="/shop/idinfo.html">회원정보변경</a></li>
							<li><a href="javascript:userexit();">회원정보탈퇴신청</a></li>
						</ul>
					</div>
				</div>
			</div>
			<!-- .lnb-wrap -->
		</div>
		<!-- #aside -->
		<hr>
		<div id="content">
			<div id="myOrder">
				<div class="tit-page-2">
					<h2>주문내역</h2>
					<p class="dsc">
						<span class="fc-blue">[이름]</span>님께서 주문하신 내역입니다.
					</p>
				</div>
				<div class="page-body">
					<!-- 주문 내역 리스트 -->
					<div class="table-d2-list">
						<table summary="번호, 주문일자, 상품명, 결제금액, 주문상세, 배송현황">
							<caption>주문내역</caption>
							<colgroup>
								<col width="70">
								<col width="100">
								<col width="*">
								<col width="100">
								<col width="90">
								<col width="90">
							</colgroup>
							<thead>
								<tr>
									<th scope="row"><div class="tb-center">번호</div></th>
									<th scope="row"><div class="tb-center">주문일</div></th>
									<th scope="row"><div class="tb-center">상품명</div></th>
									<th scope="row"><div class="tb-center">결제금액</div></th>
									<th scope="row"><div class="tb-center">주문상세</div></th>
									<th scope="row"><div class="tb-center">배송현황</div></th>
								</tr>
							</thead>
							<tbody>
<!-- 								<tr> -->
<!-- 									<td colspan="6"><div class="tb-center">주문내역이 없습니다.</div></td> -->
<!-- 								</tr> -->
								<tr>
									<td scope="row"><div class="tb-center">20201113</div></td>
									<td scope="row"><div class="tb-center">2020.11.13</div></td>
									<td scope="row"><div class="tb-center">상품명</div></td>
									<td scope="row"><div class="tb-center">결제금액</div></td>
									<td scope="row"><div class="tb-center">주문상세</div></td>
									<td scope="row"><div class="tb-center">배송현황</div></td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- //주문 내역 리스트 -->

					<!-- 주문 내역 페이징 -->
					<div class="paging"></div>
					<!-- //주문 내역 페이징 -->

					<!-- 주문 내역 info -->
					<ul class="foot-dsc">
						<li>- 상품명 또는 주문상세의 조회 버튼을 클릭하시면, 주문상세 내역을 확인하실 수 있습니다.</li>
						<li>- 배송현황의 조회 버튼을 클릭하시면, 해당 주문의 배송 현황을 한눈에 확인하실 수 있습니다.</li>
					</ul>
					<!-- //주문 내역 info -->

				</div>
				<!-- .page-body -->
			</div>
			<!-- #myOrder -->
		</div>
		<!-- #content -->
	</div>
	<!-- #contentWrap -->
</div>


<!-- 주문상세 끝 -->



<jsp:include page="../footer.jsp" />