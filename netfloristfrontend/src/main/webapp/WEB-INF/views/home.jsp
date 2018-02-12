<!-- DataTable Bootstrap Script -->
<script src="${js}/angular.js"></script>

<!-- DataTable Bootstrap Script -->
<script src="${js}/productsController.js"></script>
<div class="container" ng-app="ShoppingApp"
	ng-controller="ProductController as pCtrl">

	<div class="row" ng-init="pCtrl.fetchProducts()">

		<div class="col-md-3">
			<%@include file="./shared/sidebar.jsp"%>
		</div>

		<div class="col-md-9">

			<div class="row carousel-holder">

				<div class="col-md-12">
					<div id="carousel-example-generic" class="carousel slide"
						data-ride="carousel">
						<ol class="carousel-indicators">
							<li data-target="#carousel-example-generic" data-slide-to="0"
								class="active"></li>
							<li data-target="#carousel-example-generic" data-slide-to="1"></li>
							<li data-target="#carousel-example-generic" data-slide-to="2"></li>
							<li data-target="#carousel-example-generic" data-slide-to="3"></li>
						</ol>
						<div class="carousel-inner">

							<div class="item active">
								<img class="slide-image" src="${images}/banner1.jpg" alt="">
							</div>
							<div class="item">
								<img class="slide-image" src="${images}/banner2.jpg" alt="">
							</div>
							<div class="item">
								<img class="slide-image" src="${images}/banner3.jpg" alt="">
							</div>
							<div class="item">
								<img class="slide-image" src="${images}/banner4.jpg" alt="">
							</div>
						</div>
						<a class="left carousel-control" href="#carousel-example-generic"
							data-slide="prev"> <span
							class="glyphicon glyphicon-chevron-left"></span>
						</a> <a class="right carousel-control"
							href="#carousel-example-generic" data-slide="next"> <span
							class="glyphicon glyphicon-chevron-right"></span>
						</a>
					</div>
				</div>

			</div>



			<div class="row is-table-row">

			 	<div class="col-sm-4">
					<div class="thumbnail">
						<a href="${contextRoot}/show/category/2/products"><img alt=""
						src="${images}/products1.jpg"></a>
					</div>

				</div> 
				
				<div class="col-sm-4">
					<div class="thumbnail">
						<a href="${contextRoot}/show/category/1/products"><img alt=""
						src="${images}/products2.jpg"></a>
					</div>

				</div> 
				
				<div class="col-sm-4">
					<div class="thumbnail">
						<a href="${contextRoot}/show/category/4/products"><img alt=""
						src="${images}/products3.jpg"></a>
					</div>

				</div> 
				
				<div class="col-sm-4">
					<div class="thumbnail">
						<a href="${contextRoot}/show/7/product"><img alt=""
						src="${images}/rose1.jpg"></a>
					</div>

				</div> 
				
				<div class="col-sm-4">
					<div class="thumbnail">
						<a href="${contextRoot}/show/12/product"><img alt=""
						src="${images}/rose3.png"></a>
					</div>

				</div> 
				
				<div class="col-sm-4">
					<div class="thumbnail">
						<a href="${contextRoot}/show/10/product"><img alt=""
						src="${images}/rose4.jpg"></a>
					</div>

				</div> 
				
				<div class="col-sm-4">
					<div class="thumbnail">
						<a href="${contextRoot}/show/23/product"><img alt=""
						src="${images}/birthday2.jpg"></a>
					</div>

				</div> 
				
				<div class="col-sm-4">
					<div class="thumbnail">
						<a href="${contextRoot}/show/41/product"><img alt=""
						src="${images}/chco2.jpg"></a>
					</div>

				</div> 
				
				<div class="col-sm-4">
					<div class="thumbnail">
						<a href="${contextRoot}/show/27/product"><img alt=""
						src="${images}/oc1.jpg"></a>
					</div>

				</div>
				
				
				
			<div class="row is-table-row">

				<div class="col-sm-4" ng-repeat="product in pCtrl.mpProducts">
					<div class="thumbnail">
						<img ng-src="${images}/{{product.code}}.jpg"
							alt="{{product.name}}" class="landingImg">
						<h5>{{product.name}}</h5>
						<hr />
						<div class="caption">
							<h4 class="pull-right">'8&#36; {{product.unitPrice}}</h4>
							<p>{{product.description}}</p>
							<a ng-href="${contextRoot}/show/{{product.id}}/product"
								class="btn btn-primary pull-right">View</a>
						</div>
					</div>
				</div>

			<!-- 	<div class="col-sm-4 col-lg-4 col-md-4">
					<h4>Checkout more products!</h4>
					<hr />
					<a class="btn btn-primary" href="${contextRoot}/show/all/products">More
						Products</a>
				</div> -->

			</div>

		</div>

	</div>

</div>
<div>

	<h5>
		<strong> NetFlorist</strong>
	</h5>
	<ul>
		<li><a href="${contextRoot}/login">Login /Register</a></li>
		<li><a href="${contextRoot}/about">About Us</a></li>
		<li><a href="http://www.netjewel.co.za/">NetJewel</a></li>
	</ul>
</div>
<!-- /.container -->
