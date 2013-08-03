<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="Content-Language" content="en-us" />
<title>upload file</title>
    <script src="../lib/prototype.js" type="text/javascript"></script>    
    <script src="../lib/scriptaculous.js?load=builder,dragdrop" type="text/javascript"></script>
    <script src="../cropper.js" type="text/javascript"></script>
    
    <script type="text/javascript" charset="utf-8">
        
        function onEndCrop( coords, dimensions ) {
            $( 'x1' ).value = coords.x1;
            $( 'y1' ).value = coords.y1;
            $( 'x2' ).value = coords.x2;
            $( 'y2' ).value = coords.y2;
            $( 'width' ).value = dimensions.width;
            $( 'height' ).value = dimensions.height;
        }
        
        // example with a preview of crop results, must have minimumm dimensions
        function onClickButton(){
        new Cropper.ImgWithPreview( 
                    'cutimg',
                    { 
                        minWidth: 200, 
                        minHeight: 120,
                        ratioDim: { x: 200, y: 120 },
                        displayOnInit: true, 
                        onEndCrop: onEndCrop,
                        previewWrap: 'previewArea'
                    } 
                );
        }
        
        /*
        if( typeof(dump) != 'function' ) {
            Debug.init(true, '/');
            
            function dump( msg ) {
                // Debug.raise( msg );
            };
        } else dump( '---------------------------------------/n' );
        */
        
    </script>
    <link rel="stylesheet" type="text/css" href="debug.css" media="all" />
    <style type="text/css">
        label { 
            clear: left;
            margin-left: 50px;
            float: left;
            width: 5em;
        }
        
        #img {
            width: 500px;
            float: left;
            margin: 20px 0 0 50px; /* Just while testing, to make sure we return the correct positions for the image & not the window */
        }
        
        #previewArea {
            margin: 20px; 0 0 20px;
            float: left;
        }
        
        #results {
            clear: both;
        }
    </style>
</head>
<body>

<form action="upload.jsp" id="form1" name="form1" encType="multipart/form-data"  method="post" target="hidden_frame" >
    <input type="file" id="file" name="file" style="width:450">
    <INPUT type="submit" value="上传文件"><font color="red">支持JPG,JPEG,GIF,BMP,SWF,RMVB,RM,AVI文件的上传</font><br /><span id="msg"></span>   <div id="img"></div><div id="previewArea"></div>
    
    <br>
               
    <iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>
</form>
<form action="../CiServlet" method="post" name="form2">
<div id="results">

            <input type="hidden" name="x1" id="x1" />
            <input type="hidden" name="y1" id="y1" />
            <input type="hidden" name="x2" id="x2" />
            <input type="hidden" name="y2" id="y2" />
            <input type="hidden" name="width" id="width" />
            <input type="hidden" name="height" id="height" />
            <input type="hidden" name="filename" id="filename" />

    </div><input name="11" type="button" onclick="onClickButton()" value="截图"/>
    <input type="submit" name="button" id="button" value="保存" /></form>

</body>
</html>

<script type="text/javascript">
function callback(msg,i,path)
{
    document.getElementById("file").outerHTML = document.getElementById("file").outerHTML;
    document.getElementById("msg").innerHTML = "<font color=red>"+msg+"</font>";
    if(i == "1"){
    insertimg(path);
    }
}
function insertimg(path)
{
    document.getElementById("img").innerHTML = "<img src=/"../upload/"+path+"/" alt=/"test image/" id=/"cutimg/" />";
    document.getElementById("filename").value = path;
}
</script>