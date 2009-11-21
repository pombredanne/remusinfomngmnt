 <xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
     <xsl:template match="key[@commandId='org.eclipse.ui.actionSet.keyBindings']">
     </xsl:template>
     <!-- 
     <xsl:template match="key[@commandId='org.eclipse.ui.window.nextView']">
     </xsl:template>
     <xsl:template match="key[@commandId='org.eclipse.ui.window.previousView']">
     </xsl:template>
     <xsl:template match="key[@commandId='org.eclipse.ui.window.nextPerspective']">
     </xsl:template>
     <xsl:template match="key[@commandId='org.eclipse.ui.window.previousPerspective']">
     </xsl:template>
      -->
     <xsl:template match="node()|@*">
         <xsl:copy>
             <xsl:apply-templates select="node()|@*"/>
         </xsl:copy>
     </xsl:template>
 </xsl:stylesheet>