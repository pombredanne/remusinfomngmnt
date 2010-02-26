 <xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
     <xsl:template match="extension[@point='org.eclipse.ui.actionSets']">
     </xsl:template>
     <xsl:template match="extension[@point='org.eclipse.ui.newWizards']">
     </xsl:template>
     <xsl:template match="extension[@point='org.eclipse.ui.preferencePages']">
     </xsl:template>
     <xsl:template match="node()|@*">
         <xsl:copy>
             <xsl:apply-templates select="node()|@*"/>
         </xsl:copy>
     </xsl:template>
 </xsl:stylesheet>