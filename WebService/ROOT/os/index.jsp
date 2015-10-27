<%-- 
    Document   : index
    Created on : 7 Apr, 2014, 12:56:11 PM
    Author     : zerone
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/jquery-1.11.0.js"></script>
        <script type="text/javascript">
            $(function() {
                $('input').filter(function() {
                    return this.type == 'range'
                }).each(function() {
                    var $slider = $(this),
                            $text_box = $('#' + $(this).attr('link-to'));

                    $text_box.val(this.value);

                    $slider.change(function() {
                        $text_box.val(this.value);
                    });

                    $text_box.change(function() {
                        $slider.val($text_box.val());
                    });

                });
            });
        </script>
	
	<SCRIPT type="text/javascript">
	<!--

	function trim(s){
	var res = s.replace(/^\s+/, '');
	 return res.replace(/\s+$/, '');

	}

	function doCount(){
	document.form.CharacterCount.value=document.form.text.value.length;
	wordcounter=0;
	var v = trim(document.form.text.value);
	for (x=0;x<v.length;x++) {
	     if (v.charAt(x) == " " && v.charAt(x-1) != " ")  {wordcounter++}  
	document.form.WordCount.value=wordcounter+1;
	      }
	document.form.WordBudget.setAttribute("max", document.form.WordCount.value);
	return false;   
	}


	-->
	</SCRIPT>

        <title>OSum</title>
    </head>
    <body>
        <h1>OSum</h1>
        <h2>Opinion Summarization Tool:</h1>
        <h3>Subjectivity vs Relevance trade-off using Submodular Framework <sup><a href="#fn1" id="ref1">1</a></sup></h3>
        <form name="form" action="response.jsp" method="post">
            Function <sup><a href="#fn2" id="ref1">2</a></sup> : <select name="Submodular Function">
                <option value='1'>A1: Modular</option>
                <option value='2'>A2: Budget-additive</option>
                <option value='3'>A3: Polarity Partitioned Budget-additive</option>
                <option value='4'>A4: Facility Location</option>
                <option value='6'>A5: Polarity Partitioned Facility Location</option>
            </select>
            <p>
                &#945 (trade-off) <sup><a href="#fn3" id="ref1">3</a></sup>: 
                <input id="rangeValue" type="text" size="2"></input>
                <sub>Subjectivity</sub>

                <input id="slider" name ="alpha" type="range" min="0.00" max="1.00" step="0.01" value ="0.5" link-to="rangeValue" /> 

                <sub>Relevance</sub>



            <p>
                r (scaling factor) <sup><a href="#fn4" id="ref1">4</a></sup>:
                <input type="text" name="r" value="1.0" size="2" />
	    
	    <p>
		Word Budget: 
		<input maxlength=10 size=9 name="WordBudget" type="number" value=200>

            <p>    
                Enter the movie review:
                <!-- <input type="text" name="name" /> -->
            <p>
                <textarea onKeyUp=doCount() name="text" rows="15" cols="150">I applaud the Russo brothers and the writers Christopher Markus and Stephen McFeely on a job well done in this action thriller The action is riveting as are the quite scenes between the characters. There are Good humorous punches in this script also. The returning cast are great and expanding their roles from other movies, I am trying to be vague here as not to ruin the movie. Also there are some great additions to the cast as well. Anthony Mackie does a soaring job as the Falcon (pun intended), he has a very comfortable fit to the cast and no way feel forces in. Robert Redford is a very compelling character and presence on screen. The action scenes were fantastic when I saw the resume of the Russos's movies I was a little worried, now I think they might have to worry about being type cast as action movie directors. There are a few changes from the comic book story line but they are not distracting. The Winter Solider is a movie that greatly expands and shakes the foundation of the Marvel Cinematic Universe. Stay for the two end credit sequences. I really enjoyed this movie and recommend it. If it's action you want. Go see the Captain.</textarea>
	<p>
                    <input maxlength=10 size=9 name="WordCount" type="number" value=209 readonly>
                      words</p>
                    <p>
                      <input maxlength=10 size=9 name="CharacterCount" type="number" value=1153 readonly>
                      characters</p>
                    <p>
                <input type="submit" value="OK" />
            <p>
	</form>
            <hr></hr>

            <sup id="fn1">1. Research supported and funded by <a href="http://www.cfilt.iitb.ac.in/" title = "Gratitute">CFILT, IIT Bombay</a>. Designed and developed by <a href="http://in.linkedin.com/in/jayanthjaiswal/" title="author">Jayanth </a> and <a href="http://in.linkedin.com/in/osjayaprakash/" title="developer"> Jayaprakash S</a>.<a href="#ref1" title="Jump back to footnote 1 in the text.">↩</a></sup>
            <p>        
                <sup id="fn2">2. Refer <a href="http://www.cse.iitb.ac.in/~jayanthjaiswal/btp/acl2014.pdf" title="link to publication">ACL paper</a> for further information<a href="#ref2" title="Jump back to footnote 2 in the text.">↩</a></sup>
            <p>
                <sup id="fn3">3. F(S) = &#945L(S) + (1-&#945)R(S), Higher the alpha, more is the weightage to relevance and lower is the weightage to subjectivity  <a href="#ref3" title="Jump back to footnote 3 in the text.">↩</a></sup>
            <p>
                <sup id="fn4">4. Higher the scaling factor, more is the penalty on sentence due to its length. Refer <a href="http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.190.6425" title=""> Multi-document summarization via budgeted maximization of submodular functions</a> for insight into the role of scaling factor in the greedy algorithm<a href="#ref4" title="Jump back to footnote 4 in the text.">↩</a></sup>
                </body>
                </html>
