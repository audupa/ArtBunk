<html>
<head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
</head>
<body>
<FORM action="image/upload"
      enctype="multipart/form-data"
      method="post">
    <P>
        What files are you sending?
        Name:<input type="text" name="name"><BR>
        description:<input type="text" name="description"><br/>
        category:<input type="text" name="category"><br/>
        image cost:<input type="text" name="image_cost"><br/>
        medium:<input type="text" name="medium"><br/>
        user name:<input type="text" name="user_name"><br/>
        <INPUT type="file" name="file"><BR>
        <INPUT type="submit" value="Send"> <INPUT type="reset">
</FORM>
<div id="data"></div>
<script type="text/javascript">
    $.ajax({
        type: "GET",
        url: "allImages",
        dataType: "json",

        success:function(response){
            var dataArray = [];
            var count=0;
            $.each(response, function (index, value) {
                count++;

                for( key in value )
                    dataArray.push([key.toString(), value [key]]);
            });
            $("#data").html(count);
        }
    });
</script>

</body>
</html>
