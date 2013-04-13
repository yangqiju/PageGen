<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${clazzinfo.tablecomments}</title>

</head>
<script src="../js/dojo/dojo.js"
	data-dojo-config="parseOnLoad: true">
</script>
<script type="text/javascript" src="../js/nights/header.js"> </script>
<script type="text/javascript">
	dojo.require("dojo.parser");
	dojo.require("dojo/store/Memory");
	dojo.require("dojo.data.ItemFileWriteStore");
	dojo.require("dojox.grid.DataGrid");
	dojo.require("dijit/Dialog");
	dojo.require("dijit/form/Button");
	dojo.require("dijit/form/TextBox");
	dojo.require("dijit/form/DateTextBox");
	dojo.require("dijit/form/TimeTextBox");
	dojo.require("dijit.form.NumberTextBox");
	dojo.require("dijit/form/FilteringSelect");
	dojo.require("dijit/form/ValidationTextBox");
	dojo.require("dojox.widget.Standby");
	
	require([ "dojo/ready", "dojo/dom","nights/TestDateBox"], function(ready,dom,TestDateBox) {
		ready(function() {
<#assign datesearchindex=-1 >    
<#list clazzinfo.fields as field>
	<#if field.type == "TIMESTAMP(6)"||field.type=="DATE">
		<#if  field.name != "createdate" >
				new TestDateBox({
	            value: "2012-01-01",
	            name: "${field.name}"
	        }, "${field.name}");
			</#if>
			
	         new TestDateBox({
		            value: "now()",
		            name: "s_${field.name}"
		        }, "s_${field.name}");
				
	        <#if datesearchindex==-1>
	        	<#assign datesearchindex=field_index >
	        	<#assign datesearchfield=field >
	        	
		        new TestDateBox({
		            value: "now()",
		            name: "ss_${field.name}"
		        }, "ss_${field.name}");
				
				new TestDateBox({
		            value: "now()",
		            name: "se_${field.name}"
		        }, "se_${field.name}");
	        </#if>
	</#if>
	<#if field.ref >
	
	new dijit.form.FilteringSelect({
				id : "${field.name}",
				identifier: '${field.refKeyField}',
				searchAttr: '${field.refLabelField}',
				store : new dojo.data.ItemFileReadStore({url:"../${field.refPath}/list.do?start=0&count=200&sortcolumn=${field.refLabelColumn}&desc=false"}),
				name : "${field.name}",
				autoComplete : true,
				style : "width: 150px;",
			}, "${field.name}");
	</#if>
</#list>  
	        

	    });
	});

</script>

<#assign slinecount=0 >
<#assign count=0 >
<#list clazzinfo.fields as field>
		<#if field_index=datesearchindex >
		<#elseif field.name != "uuid" && count <6 >
			<#assign count=count+1>
			<#if count==3 >
				<#assign slinecount=slinecount+1 >
			</#if>
		<#else>
		</#if>
</#list> 
	<#if (count>3) >
<#assign slinecount=2 >
</#if>

<body class="claro">
	<div class="S_Nva f_s_12">
		<a href="../ext/welcome.html">首页</a> >> <a href="">${menuName}</a> >> <span>${clazzinfo.tablecomments}</span>
	</div>
<#if (slinecount>=2) >
	<div class="S_Other f_s_12" style="height: 109px">
<#else>
	<div class="S_Other f_s_12" style="height: 79px">
</#if>
	<form id="searchform">
		<div class="S_OtherTop">
			<#if (datesearchindex >=0) >
				${datesearchfield.comments}:<input id="ss_${datesearchfield.column}" name="ss_${datesearchfield.column}" type="text"
				class="txt1" /> 到  <input  id="se_${datesearchfield.column}" name="se_${datesearchfield.column}" type="text" class="txt1"/>
			</#if>
			<input type="button" id="newBtn" class="btnNew1 btnZoom" 
			style="background: url(../images/add.png);margin-left:20px;" />
			<input type="button" id="searchbtn" class="btnNew1 btnZoom"
				style="background: url(../images/save.png);margin-left:110px;" />
			<input type="button" id="exportBtn" class="btnNew1 btnZoom" 
			style="background: url(../images/save.png);margin-left:200px;" />
			<input type="button" id="deleteSelected" class="btnNew1 btnZoom" 
			style="background: url(../images/cancel.png);margin-left:290px;" />
		</div>


<#if (slinecount>=2) >
	<div class="S_OtherBottom" style="height: 66px">
	<#assign rowcount=2 >
<#else>
	<div class="S_OtherBottom" style="height: 33px">
	<#assign rowcount=1 >
	
</#if>

<#assign count=0 >
		<table  style="margin-left: 8px">
			<tr align="right">	
<#list clazzinfo.fields as field>
		<#if field_index=datesearchindex >
		<#elseif field.name != "uuid" && count <6 >
			<td>${field.comments}:</td> 
			<#if field.hasOption >
				<td><select data-dojo-type="dijit/form/FilteringSelect" name="s_${field.column}" id="s_${field.column}" class="dojoselect">
					<option value="" selected="selected">全部</option>
					<#list field.options as opt>
						<option value="${opt.value}">${opt.label}</option>
					</#list>
				</select></td>
			<#else>
					<td><input name="s_${field.name}" id="s_${field.name}" type="text"
					class="txt2" /></td>	
			</#if>
			<#assign count=count+1>
			<#if count==3 >
				<#if (rowcount==2) >
					</tr><tr>
				</#if>
			</#if>
		<#else>
		</#if>
</#list> 
		</tr></table>
			
		</div>
		</form>
	</div>
	<div id="basic4" class="S_Other">
		<div id="gridDiv" class="claro" ></div>
	</div>
	<div data-dojo-id="basicStandby" data-dojo-type="dojox.widget.Standby"
		data-dojo-props="target:'basic4', color:'lightgray', image:'../images/loading.gif'"></div>
	<div class="Page f_s_12">
		<form style="margin-left:30px;">
		每页显示<select style="width:50px" id="pagecount" data-dojo-type="dijit/form/FilteringSelect" onchange="changepagesize()" >
			<option>10</option>
			<option>15</option>
			<option selected>20</option>
			<option>30</option>
		</select>条记录
		<ul id="pageCtrl">
		</ul>
		</form>
	</div>
	<div style="clear: both;"></div>
	<!--<div class="dell f_s_12">
		<div class="S_OtherTop">
			<input type="checkbox" id="selectAll" name="checkbox2"
				value="checkbox" /> 全选 <input type="button" class="Btn btnZoom"
				id="deleteSelected"
				style="background: url(../../images/b_29dell.jpg)" />
		</div>
	</div>-->

	<div class="WinCon" data-dojo-type="dijit/Dialog"
		data-dojo-id="myFormDialog" title="修改">
		<div class="dijitDialogPaneContentArea">
			<form id="submitform">
				<table id="tbcon" border="0" cellpadding="0" cellspacing="0">
<#assign count=0>
<#list clazzinfo.fields as field>
		<#if  field.name == "uuid" >
			<input type="hidden" name="uuid" id="uuid" />
		<#elseif field.name =="createdate">
		<!-- <input type="hidden" name="createdate" id="createdate" /> -->
		<#else>
			<#if count%2 ==0>
			 <tr>
   			   <td class="td1">${field.comments}</td>
			   <td class="td2">
				<#if field.hasOption >
				<select data-dojo-type="dijit/form/FilteringSelect" name="${field.name}" id="${field.name}" class="dojoselect">
					<#list field.options as opt>
						<option value="${opt.value}">${opt.label}</option>
					</#list>
				</select>
				<#else>
					<input name="${field.name}" id="${field.name}" type="text"
					class="txt" />	
				</#if>
					</td>
			<#else>
   			    <td class="td3">${field.comments}</td>
				<td class="td4"><#if field.hasOption >
				<select data-dojo-type="dijit/form/FilteringSelect" name="${field.name}" id="${field.name}" class="dojoselect">
					<#list field.options as opt>
						<option value="${opt.value}">${opt.label}</option>
					</#list>
				</select>
				<#else>
					<input name="${field.name}" id="${field.name}" type="text"
					class="txt" />	
				</#if></td>
				</tr>
			</#if>
			<#assign count=count+1>
		</#if>
</#list> 
<#if count%2==1 >
			</tr>
</#if>
					<!-- <tr>
					<td class="td1">上传文件</td>
					<td colspan="3" class="td2"><input type="text" class="txt2" />
						<input type="button" class="Btn2" value="上传" /></td>
				</tr> -->

				</table>
			</form>
			<div style="width: 100%; height: 38px">
				<table
					style="height: 38px; width: 300px; position: relative; margin-left: 100px">
					<tr>
						<td><input name="button" type="button" id="btnWinOK"
							class="WinConok btnZoom" /></td>
						<td><input name="button" type="button"
							class="WinConCancel btnZoom" onClick="myFormDialog.hide()" /></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	
	<div class="DivShow" id="tablehover" style="display: none;">
		<table border="0" cellpadding="1" cellspacing="1">
			<tr>
				<td class="ttd2" colspan="2">详细信息</td>
			</tr>
<#list clazzinfo.fields as field>
			<tr>
				<td class="ttd1" 
			<#if  field.name == "uuid" >
				style="display: none"
			</#if> 
				>${field.comments}</td>
				<td class="ttd2" id="tt_${field.name}"></td>
			</tr>
</#list> 
		</table>
	</div>


	<div class="DivShow" id="detailhover" style="display: none;">
		<table border="0" cellpadding="1" cellspacing="1">
			<tr>
				<td class="ttd2"><div align="center">
						<strong id="detailhovertxt"></strong>
					</div></td>
			</tr>
		</table>
	</div>
	
</body>

<script type="text/javascript">
	//set up layout
	function doSearch(grid) {
		console.log("doSearch:" + grid);
	}
	var changepagesize;
	
	require([ "nights/tbgrid","nights/params","dojo/domReady!"], function(tbgrid,params) {
<#list clazzinfo.fields as field>
		<#if field.hasOption=true >
		var ${field.name}Option = function(value){
			 var options=${field.optionString};
		 	return options[value];      
		};
		</#if>	
</#list>
		var layout = [ {
			name : '选择',
			field : "checked",
			width : "30px",
			editable : true,
			styles : 'text-align: center;',
			type : dojox.grid.cells.Bool,
		}
<#list clazzinfo.fields as field>
		<#if  field.name != "uuid" >
		, {
			name : '${field.comments}',
			field : '${field.name}',
		<#if field.type == "TIMESTAMP(6)"||field.type="DATE">
			formatter: tbgrid.formatDate,
		<#elseif field.hasOption=true >
			formatter: ${field.name}Option,
		</#if>	
			desc:true,
			column : '${field.column}',
		<#if field.hasTooltip=true >
			<#if field.hasOption=false >
			formatter : tbgrid.linkformatter,
			</#if>
			tooltipid : "${field.tttype}hover",
		</#if>
		}
		</#if>
</#list> 
		,{
			name : '修改',
			field : 'modify',
			width : "30px",
			formatter: function(cellValue, rowIndex){
			    // return needed images for rowIndex of column 'col1'
			    return '<img src="../images/save.png" />';
			}
		}
];
		var notNullAble = [];
<#list clazzinfo.fields as field>
		<#if  field.nullAble == "N" >
			notNullAble.push('${field.column}');
		</#if>
</#list>
		
		tbgrid.setNotNullableStr(notNullAble);
		//tbgrid.setPriv(params.getStr("priv"));
		tbgrid.setReadOnly(false);
		tbgrid.loadOnStart(layout,'/${project}/${domainObject}','${clazzinfo.primaryKey}');
		changepagesize=tbgrid.changepagesize;
		
	});
	//dojo.require("dojox.grid.enhanced.plugins.IndirectSelection");

</script>
</html>