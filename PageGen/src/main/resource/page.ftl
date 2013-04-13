<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>${clazzinfo.tablecomments}</title>
<style type="text/css">
@import "../js/dojo/resources/dojo.css";
@import "../js/dijit/themes/claro/claro.css";
@import "../js/dojox/grid/enhanced/resources/claro/EnhancedGrid.css";
@import "../js/dojox/grid/enhanced/resources/EnhancedGrid_rtl.css";
</style>
<link rel="stylesheet" href="../js/dijit/themes/claro/claro.css">
<link rel="stylesheet" href="../js/dojo/resources/dojo.css" />
<link rel="stylesheet" href="../js/dojox/grid/resources/claroGrid.css" />
<link rel="stylesheet" href="../../../resources/style/demo.css"
	media="screen">
<link rel="stylesheet" href="base.css" />
<style type="text/css">
@import "../js/dojox/grid/resources/claroGrid.css";
/*Grid needs an explicit height by default*/
#grid {
	height: 35em;
}
</style>
<script>
	dojoConfig = {
		parseOnLoad : true
	}
</script>
<script src="../js/dojo/dojo.js"></script>
<script>
	require([ "dojo/parser", "dijit/Dialog", "dijit/form/Button",
			"dijit/form/TextBox", "dijit/form/DateTextBox",
			"dijit/form/TimeTextBox","dijit/form/ValidationTextBox","dijit/form/Form" ]);
</script>
<style type="text/css">
body {
	width: 72%;
	height: 100%;
	margin: 0 auto;
	text-align: center;
}
</style>
</head>
<body id="body" class="claro">
	<div id="page">
		<div class="store-header clearfix">
			<div class="masthead clearfix">
				<h2>
					<a href="/cn"> Home </a>
				</h2>
			</div>
			<div class="breadcrumb-nav">
				<ol>
					<li><a href="/cn" onclick=""> <span>主页</span>
					</a>>><a href="/cn" onclick=""> <span>管理</span>
					</a>>><a href="/cn" onclick=""> <span>${clazzinfo.tablecomments}</span>
					</a></li>
				</ol>
				<!-- 
				<div class="user-navigation" id="guide_wrap">
					<ul>
						<li id="u_help"><a href="" onclick="">帮助</a></li>
						<li id="u_account"><a href="" onclick="">我的帐户</a></li>
						<li id="u_cart" class="cart"><a href="" onclick="">购物车</a></li>
					</ul>
				</div>
				 -->
			</div>
		</div>
	</div>
	<br />
	<div id="button">
		<!-- addform----------------------------------------------------------------- -->
		<div data-dojo-type="dijit/Dialog" data-dojo-id="addDialog"
			title="添加">
			<script type="dojo/on" data-dojo-event="hide" data-dojo-args="e">
           			 myForm.reset();
      		  </script>
			<form data-dojo-type="dijit/form/Form" data-dojo-id="myForm" id="myForm">
				<script type="dojo/on" data-dojo-event="submit" data-dojo-args="e">
        			    e.preventDefault(); // prevent the default submit
          			  if(!myForm.isValid()){ alert('Please fix fields'); return; }
           			 window.alert("Would submit here via dojo/xhr");
					dojo.xhrPost({
        			    url: "/${project}/${domainObject}/create",
         			   form: "myForm",
        			    load: function(data){
							alert(JSON.parse(data).description);
							addDialog.hide();
        			    }
      				  });
      		  </script>
				<table class="dijitDialogPaneContentArea">
				<#list clazzinfo.fields as field>
					<#if field.name!="createdate"&&field.name!="updatedate"&&field.name!="uuid">
						<tr>
							<td><label>${field.comments}: </label></td>
							<#if field.type == "TIMESTAMP(6)"||field.type == "DATE">
							
								<td><input data-dojo-type="dijit/form/DateTextBox"
								data-dojo-props='constraints:{selector: "date", datePattern: "yyyy-MM-dd", locale: "en-us"}'
								data-dojo-id="add_${field.name}" type="text" style="width: 130px;"
								name="${field.name}" id="add_${field.name}"></td>
								
							<#elseif field.hasOption>
							
								<td>
								<select data-dojo-id="add_${field.name}" name="${field.name}" 
								id="add_${field.name}" style="width: 130px;" data-dojo-type="dijit/form/Select">
									<#list field.options as option>
								    <option value="${option.value}">${option.label}</option>
									</#list>
								</select>
								</td>
								
							<#else>
							
							<td><input data-dojo-type="dijit/form/ValidationTextBox"
								data-dojo-props="required:true" style="width: 130px;"
								data-dojo-id="add_${field.name}" type="text"
								name="${field.name}" id="add_${field.name}"></td>
							</#if>
						</tr>
					</#if>
				</#list>
				</table>
				
				
				<div class="dijitDialogPaneActionBar">
					<button data-dojo-type="dijit/form/Button" type="submit"
						id="addOk">add</button>
					<button id="addReset" data-dojo-type=dijit/form/Button
						data-dojo-props='type:"reset"'>Reset</button>
					<button data-dojo-type="dijit/form/Button" type="button"
						onClick="addDialog.onCancel();" id="addCancel">Cancel</button>
				</div>
			</form>
		</div>
		<!-- addform----------------------------------------------------------------- -->
		<!-- searchform----------------------------------------------------------------- -->
		<div data-dojo-type="dijit/Dialog" data-dojo-id="searchDialog"
			title="查询">
			<script type="dojo/on" data-dojo-event="hide" data-dojo-args="e">
           			 searchForm.reset();
      		  </script>
			<form data-dojo-type="dijit/form/Form" data-dojo-id="searchForm" id="searchForm">
				<table class="dijitDialogPaneContentArea">
				<#list clazzinfo.fields as field>
					<#if field.name!="createdate"&&field.name!="updatedate"&&field.name!="uuid">
						<tr>
							<td><label>${field.comments}: </label></td>
							<#if field.type == "TIMESTAMP(6)"||field.type == "DATE">
							
								<td><input data-dojo-type="dijit/form/DateTextBox"
								data-dojo-props='constraints:{selector: "date", datePattern: "yyyy-MM-dd", locale: "en-us"}'
								data-dojo-id="search_${field.name}" type="text" style="width: 130px;"
								name="${field.name}" id="search_${field.name}"></td>
								
							<#elseif field.hasOption>
							
								<td>
								<select data-dojo-id="search_${field.name}" name="${field.name}" 
								id="search_${field.name}" style="width: 130px;" data-dojo-type="dijit/form/Select">
									<#list field.options as option>
								    <option value="${option.value}">${option.label}</option>
									</#list>
								</select>
								</td>
								
							<#else>
							
							<td><input data-dojo-type="dijit/form/TextBox"
								data-dojo-props="required:true" style="width: 130px;"
								data-dojo-id="search_${field.name}" type="text"
								name="${field.name}" id="search_${field.name}"></td>
							</#if>
						</tr>
					</#if>
				</#list>
				</table>
				
				
				<div class="dijitDialogPaneActionBar">
					<button data-dojo-type="dijit/form/Button" type="submit"
						id="searchOk">search</button>
					<button id="searchReset" data-dojo-type=dijit/form/Button
						data-dojo-props='type:"reset"'>Reset</button>
					<button data-dojo-type="dijit/form/Button" type="button"
						onClick="searchDialog.onCancel();" id="searchCancel">Cancel</button>
				</div>
			</form>
		</div>
		<!-- searchform----------------------------------------------------------------- -->
		<button data-dojo-type="dijit/form/Button" type="button"
			onClick="searchDialog.show();">查询!</button>
		<button data-dojo-type="dijit/form/Button" type="button"
			onClick="addDialog.show();">添加!</button>
		<!-- deleteform----------------------------------------------------------------- -->
		<button data-dojo-type="dijit/form/Button" type="button"
			data-dojo-props="">删除!
			<script type="dojo/on" data-dojo-event="click" data-dojo-args="e">
					var items = grid.selection.getSelected();
          			  //if(items.length<=0){ alert('请选择要删除的记录.'); return; }
						//alert(JSON.stringify(items));
		require(["dojo/request"], function(request){
 			request.post("/${project}/${domainObject}/delete",{
 					data:{
 						items:JSON.stringify(items)
 					},
  				    handleAs: "json"
 				   }).then(function(data){
  				    alert(JSON.parse(data).description);
  				  },function(err){});
			});
      		  </script>
			</button>
		<!-- deleteform----------------------------------------------------------------- -->
		<!-- updateform----------------------------------------------------------------- -->
		<button data-dojo-type="dijit/form/Button" type="button">更新!
			<script type="dojo/on" data-dojo-event="click" data-dojo-args="e">
					var items = grid.selection.getSelected();
          			  //if(items.length<=0){ alert('请选择要更新的记录.'); return; }
						//alert(JSON.stringify(items));
		require(["dojo/request"], function(request){
 			request.post("/${project}/${domainObject}/update",{
   				  data:{
						items:JSON.stringify(items)
						},
  				    handleAs: "json"
 				   }).then(function(data){
  				   alert(JSON.parse(data).description);
  				  },function(err){
  				  	alert("error");
  				  });
			});
      		  </script>
      	</button>
		<!-- updateform----------------------------------------------------------------- -->
	</div>
	<div id="grid" class="grid"></div>
	<div id="content"></div>
	<div id="results2"></div>
	<script>
	dojo.require("dojox.grid.enhanced.plugins.Pagination");
		var grid, dataStore, store;
		require([ "dojox/grid/EnhancedGrid", "dojo/store/Memory",
				"dojo/data/ObjectStore", "dojo/request","dojox/grid/cells/dijit",
				"dojox/grid/_CheckBoxSelector","dojo/json","dojo/_base/lang",
				"dojo/data/ItemFileWriteStore","dojo/domReady!" ],
				function(EnhancedGrid, Memory, ObjectStore, request,lang,baseArray,JSON) {
					request.get("/${project}/${domainObject}/list", {
						handleAs : "json"
					}).then(function(data) {
						store = new Memory({
							data : data.items
						});
						dataStore = new ObjectStore({
							objectStore : store
						});
						grid = new EnhancedGrid({
							selectionMode: 'extended',
							columnReordering : true,//此属性设置为true,可以拖拽标题栏，更换列顺序
							loadingMessage : "请等待，数据正在加载中......",
							errorMessage : "对不起，你的请求发生错误!",
							store : dataStore,
							selectable:true,
							//rowSelector: '20px',
							//height: '400px',
							//width:'200px',
							keepSelection: true,
							plugins: {
						          pagination: {
						              pageSizes: ["25", "50", "100", "All"],
						              description: true,
						              sizeSwitch: true,
						              pageStepper: true,
						              gotoButton: true,
						              maxPageStep: 4,
						              position: "bottom"
						          }
							}, 
							query : {
								id : "*"
							},
							structure :
								[{
									// First, our view using the _CheckBoxSelector custom type
									type: "dojox.grid._CheckBoxSelector",
									width:"20px"
								},
							[
							new dojox.grid.cells.RowIndex({
								name : "编号",
								//width : "20px",
								editable: false
							})
					        <#list clazzinfo.fields as field>
					        <#if field.type == "TIMESTAMP(6)"||field.type == "DATE">
						        ,{
						       		name : "${field.comments}",
						         	field: "${field.name}",
						         	editable: false,
							         type: dojox.grid.cells.DateTextBox,
							         widgetProps: {selector: "date"},
							         constraint:{
							        	// min:new Date(),
							        	// max:new Date()
							         },
							        // value:new Date(),
							         formatter: function(v) {
							        	 if (v) 
							        		 return dojo.date.locale.format(new Date(v),{selector: 'date',datePattern:"yyyy-MM-dd"})
							        }
						        }
						        <#else>
						        </if field.name!="uuid">
						        	,{
									name : "${field.comments}",
									field : "${field.name}",
									//width : "100px",
									autoWidth:true
									<#if clazzinfo.primaryKey== field.name>
									,editable:false
									<#else>
									,editable: true
									</#if>
								}
						        </#if>
					        </#if>
							</#list>  
							]]
						}, "grid");
						grid.on("RowClick", function(evt) {
			                var idx = evt.rowIndex,
			                    item = this.getItem(idx),
			                    store = this.store;
			                    content = dojo.byId("content");
			                    content.innerHTML="you have clicked on rows " + store.getValue(item, "id");
			　　　　　　　　　　}, true);
						grid.startup();
						grid.on("SelectionChanged",function(node){
							var items = this.selection.getSelected();
							//alert(items.length);
							//alert(JSON.stringify(items));//Object to json
						},true);
					});
					
							
					dojo.addOnLoad(function() {
						var search = dijit.byId('searchOk');
						var searchForm = dijit.byId('searchForm');
						var fetch = function fetch(){
							     var s = "?";
								var obj = searchForm.getValues();
							    for(var item in obj){
							    	if(obj[item]!=''||obj[item]!=null){
							    	s+=item+"="+obj[item]+"&"
							    	}
							    }
							    var url="/${project}/${domainObject}/list"+s;
							request.get(url, {
									handleAs : "json"
								}).then(function(data) {
									store = new Memory({
										data : data.items
									});
									dataStore = new ObjectStore({
										objectStore : store
									});
									grid.setStore(dataStore);
									searchDialog.hide();
								});     
						}
						dojo.connect(search, 'onClick', fetch);
					});
				});
	</script>
</body>
</html>
