<html>
    <head>
        <title>Use jsTree</title>
       	<link type="text/css" rel="stylesheet" href="syntax/!style.css"/>
     	<link type="text/css" rel="stylesheet" href="!style.css"/>
	
        <script src="jquery.js">
        </script>
        <script src="jquery.jstree.js">
        </script>
        <script>
            $(document).ready(function(){
                $("#treeViewDiv").jstree({
                    "json_data" : {
                        "data":[
                            {
                                "data" : "Search engines",
                                "children" :[
                                             {"data":"Yahoo", "metadata":{"href":"http://www.yahoo.com"}},
                                             {"data":"Bing", "metadata":{"href":"http://www.bing.com"}},
                                             {"data":"Google", "children":[{"data":"Youtube", "metadata":{"href":"http://youtube.com"}},{"data":"Gmail", "metadata":{"href":"http://www.gmail.com"}},{"data":"Orkut","metadata":{"href":"http://www.orkut.com"}}], "metadata" : {"href":"http://youtube.com"}}
                                            ]
                            },
                            {
                                "data" : "Networking sites",
                                "children" :[
                                    {"data":"Facebook", "metadata":{"href":"http://www.fb.com"}},
                                    {"data":"Twitter", "metadata":{"href":"http://twitter.com"}}
                                ]
                            }
                        ]
                    },
                    "plugins" : [ "jstree/themes", "json_data", "ui" ]
                }).bind("select_node.jstree", function(e, data)
                    {
                        if(jQuery.data(data.rslt.obj[0], "href"))
                        {
                            window.location=jQuery.data(data.rslt.obj[0], "href");
                        }
                        else
                        {
                            alert("No href defined for this element");
                        }
                    })
            });
            
            
            $("#selector").jstree();
            
        </script>
    </head>
    <body>
        <div id="treeViewDiv">
        </div>
        
        <div id="selector">
  <ul>
    <li><a>Team A's Projects</a>
      <ul>
    <li><a>Iteration 1</a>
          <ul>
            <li><a>Story A</a></li>
            <li><a>Story B</a></li>
            <li><a>Story C</a></li>
          </ul>
        </li>
    <li><a>Iteration 2</a>
          <ul>
        <li><a>Story D</a></li>
          </ul>
        </li>
      </ul>
    </li>
  </ul>
</div>

    </body>
</html>