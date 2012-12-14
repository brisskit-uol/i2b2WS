 <html>                                                                  
 <head>                                                                  
 <script type="text/javascript" src="jquery.js"></script>          
 <script type="text/javascript" src="jquery.tree.js"></script>          
 <script type="text/javascript">                                         
   // we will add our javascript code here                                     
 </script>                               
 <link type="text/css" rel="stylesheet" href="jstree/_docs/syntax/!style.css"/>
 <link type="text/css" rel="stylesheet" href="jstree/_docs/!style.css"/>                                 
 </head>                                                                 
 <body>  


<table border="1">
<tr>
<td> 

<textarea id="xmlv" cols="100" rows="30">
<?xml version="1.0" encoding="UTF-8"?>
<pdo:patient_data xmlns:pdo="http://www.i2b2.org/xsd/hive/pdo/1.1/pdo">
<pdo:event_set>
<event download_date="2012-05-03T17:03:06.300+01:00" 
import_date="2012-05-03T17:03:06.300+01:00" sourcesystem_cd="BRICCS" 
update_date="2012-05-03T17:03:06.300+01:00" upload_id="1">
<event_id 
source="BRICCS">BPt00000020_2012-05-03T17:03:06.007+01:00</event_id>
<patient_id source="BRICCS">BPt00000020</patient_id>
<param column="ACTIVE_STATUS_CD" name="active status">F</param>
<param column="INOUT_CD" name="">@</param>
<param column="LOCATION_CD" name="">@</param>
<param column="LOCATION_PATH" name="">@</param>
<start_date>2012-04-24T00:00:00.000+01:00</start_date>
<end_date>@</end_date>
</event>
</pdo:event_set>
<pdo:pid_set>
<pid>
<patient_id download_date="2012-05-03T17:03:06.300+01:00" 
import_date="2012-05-03T17:03:06.300+01:00" source="BRICCS" 
sourcesystem_cd="BRICCS" status="Active" 
update_date="2012-05-03T17:03:06.300+01:00" 
upload_id="1">BPt00000020</patient_id>
</pid>
</pdo:pid_set>
<pdo:eid_set>
<eid>
<event_id download_date="2012-05-03T17:03:06.300+01:00" 
import_date="2012-05-03T17:03:06.300+01:00" source="BRICCS" 
sourcesystem_cd="BRICCS" status="Active" 
update_date="2012-05-03T17:03:06.300+01:00" 
upload_id="1">BPt00000020_2012-05-03T17:03:06.007+01:00</event_id>
</eid>
</pdo:eid_set>
<pdo:patient_set>
<patient download_date="2012-05-03T17:03:06.300+01:00" 
import_date="2012-05-03T17:03:06.300+01:00" sourcesystem_cd="BRICCS" 
update_date="2012-05-03T17:03:06.300+01:00" upload_id="1">
<patient_id source="BRICCS">BPt00000020</patient_id>
<param column="vital_status_cd" name="date interpretation code">N</param>
<param column="birth_date" 
name="birthdate">1972-04-25T00:00:00.000+01:00</param>
<param column="age_in_years_num" name="age">40</param>
<param column="race_cd" name="ethnicity">Unknown</param>
<param column="sex_cd" name="sex">MALE</param>
</patient>
</pdo:patient_set>
</pdo:patient_data>
</textarea><br>

</td>

</tr>
<tr>
<td>
<input type="button" id="myButton" value="click me" /> 
</td>

</tr>
</table> 

<select id="onts" style="width: 200px">
  <option value="0">Select an ontology...</option>
</select> 

<div id="demo1" class="demo" style="height:100px;">
	<ul>
		<li id="phtml_1">
			<a href="#">Root node 1</a>
			<ul>
				<li id="phtml_2">
					<a href="#">Child node 1</a>
				</li>
				<li id="phtml_3">
					<a href="#">Child node 2</a>
				</li>
			</ul>
		</li>
		<li id="phtml_4">
			<a href="#">Root node 2</a>
		</li>
	</ul>
</div>


<script>

$("#onts").bind("keydown change", function(){
    var box = $(this);
    setTimeout(function() {
    	//alert(box.val());
    	
    }, 0);
});
/*
$('#onts').keyup(function() {
	  $(this).change();
	});
	
$("#onts").change(function() {
    var value = $(this).val();
    alert(value);
    // display based on the value
});
*/
$(document).ready(function() {
    $('#xmlv').hide();

    $("#demo1").jstree({ 
		"json_data" : {
			"data" : [
				{ 
					"data" : "A node", 
					"metadata" : { id : 23 },
					"children" : [ "Child 1", "A Child 2" ]
				},
				{ 
					"attr" : { "id" : "li.node.id1" }, 
					"data" : { 
						"title" : "Long format demo", 
						"attr" : { "href" : "#" } 
					} 
				}
			]
		},
		"plugins" : [ "themes", "json_data", "ui" ]
	}).bind("select_node.jstree", function (e, data) { alert(data.rslt.obj.data("id")); });


});

$('#myButton').click(function(){
    var xmlContent = $('#xmlv').val();
    //$.ajax({  contentType: "application/xml; charset=utf-8", type: "POST", url: "http://localhost:8080/i2b2WS/rest/service/pdo", data: { xml : incomingXML}, success: function(msg) {  alert(msg); }, });
    
    // orig
    //$.ajax({  contentType: "application/xml; charset=utf-8", type: "POST", url: "http://localhost:8080/i2b2WS/rest/service/pdo", data: {incomingXML: xmlContent, activity_id: "117"}, success: function(msg) {  alert(msg); }, });
    
    
    
    //$.ajax({  contentType: "application/xml; charset=utf-8", type: "POST", url: "http://localhost:8080/i2b2WS/rest/service/i2b2callback", data: {incomingXML: xmlContent}, success: function(msg) {  alert(msg); }, });
    
    
    var xmlContent1 = "{1}";
    //$.ajax({  contentType: "application/json; charset=utf-8", type: "GET", url: "http://localhost:8080/i2b2WS/rest/service/i2b2callback1", data: {incomingXML: xmlContent1}, success: function(msg) {  alert(msg); }, });
    //$.get("http://localhost:8080/i2b2WS/rest/service/i2b2callback1/"+xmlContent1, function (response){alert(response);});
    
    //$.ajax({  contentType: "application/text; charset=utf-8", type: "GET", url: "http://localhost:8080/i2b2WS/rest/service/i2b2callback1/{1}", success: function(msg) {  alert("zz"+msg); }, });

/*    
    var jqxhr = $.ajax( "http://localhost:8080/i2b2WS/rest/service/ont/1000000001,1000000002" )
    .done(function() { alert("success"); })
    .fail(function() { alert("error"); })
    .always(function() { alert("complete"); });
    
    alert(jqxhr.name);
    
    var obj = jQuery.parseJSON(jqxhr);
    alert( obj.name );
    
    */
    
     
    $.getJSON("http://localhost:8080/i2b2WS/rest/service/ont/00001,1000000002",function(data)
			{	
				// Sort the returned conflicts in alphabetical order.
				
				//alert(data);
				
				/*
				(data.results).sort(function(a,b)
				{
					return ((a.optionDisplay == b.optionDisplay) ? 0 : ((a.optionDisplay > b.optionDisplay) ? 1 : -1));
				});
				*/
									
			 	$.each(data,function(index,ontology)
				{  				
					//alert(ontology.c_fullname);
					//alert(ontology.c_hlevel);
					
					
					$('#onts').append($('<option>', { 
					    value: ontology.c_hlevel, 
					    text : ontology.c_fullname 
					  }
					));
					
			    });
			    
			 
				
			});
    
    

    
    
    
    
    $.getJSON("http://localhost:8080/i2b2WS/rest/service/getonttree/catissue",function(data)
			{	
				// Sort the returned conflicts in alphabetical order.
				
				//alert(data);
				
				/*
				(data.results).sort(function(a,b)
				{
					return ((a.optionDisplay == b.optionDisplay) ? 0 : ((a.optionDisplay > b.optionDisplay) ? 1 : -1));
				});
				*/
									
			 	$.each(data,function(index,onttree)
				{  				
					//alert(ontology.c_fullname);
					//alert(ontology.c_hlevel);
					
					/*
					$('#onts').append($('<option>', { 
					    value: ontology.c_hlevel, 
					    text : ontology.c_fullname 
					  }
					));
					*/
			    });
			    
			 
				
			});

    
    
    
    
}); 





//$.post("http://localhost:8080/Myi2b2WS2/rest/test/postjsonjson", {"incomingXML":incomingXML}, function (response){alert(response);});
//$.get("http://localhost:8080/Myi2b2WS2/rest/test/getjsonjson", {"incomingXML":incomingXML}, function (response){alert(response);});

//$.post("http://localhost:8080/Myi2b2WS2/rest/test/xmltest", {"incomingXML":incomingXML}, function (response){alert(response);});

//$.ajax({  contentType: "application/xml; charset=utf-8", type: "POST", url: "http://localhost:8080/Myi2b2WS2/rest/test/xmltest2", data: { xml : incomingXML}, success: function(msg) {  alert(msg); }, });


</script>                                        
</body>                                                                 
</html>
