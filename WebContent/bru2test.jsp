  <html>                                                                  
 <head>                                                                  
 <script type="text/javascript" src="jquery-1.7.1.min.js"></script>          
 <script type="text/javascript">                                         
   // we will add our javascript code here                                     
 </script>                                                               
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



<script>

$('#myButton').click(function(){
    var xmlContent = $('#xmlv').val();
    //$.ajax({  contentType: "application/xml; charset=utf-8", type: "POST", url: "http://bru2.brisskit.le.ac.uk:8080/i2b2WS/rest/service/pdo", data: { xml : incomingXML}, success: function(msg) {  alert(msg); }, });
    $.ajax({  contentType: "application/xml; charset=utf-8", type: "POST", url: "http://" + window.location.hostname  + ":8080/i2b2WS/rest/service/pdo", data: {incomingXML: xmlContent, activity_id: "117"}, success: function(msg) {  alert(msg); }, });
    
}); 

//$.post("http://localhost:8080/Myi2b2WS2/rest/test/postjsonjson", {"incomingXML":incomingXML}, function (response){alert(response);});
//$.get("http://localhost:8080/Myi2b2WS2/rest/test/getjsonjson", {"incomingXML":incomingXML}, function (response){alert(response);});

//$.post("http://localhost:8080/Myi2b2WS2/rest/test/xmltest", {"incomingXML":incomingXML}, function (response){alert(response);});

//$.ajax({  contentType: "application/xml; charset=utf-8", type: "POST", url: "http://localhost:8080/Myi2b2WS2/rest/test/xmltest2", data: { xml : incomingXML}, success: function(msg) {  alert(msg); }, });


</script>                                        
</body>                                                                 
</html>