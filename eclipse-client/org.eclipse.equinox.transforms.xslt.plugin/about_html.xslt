<!-- 
pointless transform that illustrates that even files that are accessed via File urls
(in external web browsers in particular) still respond to transforms. 
-->
 <xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
     <xsl:template match="node()|@*">
     Modified
         <xsl:copy>
             <xsl:apply-templates select="node()|@*"/>
         </xsl:copy>
     </xsl:template>
 </xsl:stylesheet>