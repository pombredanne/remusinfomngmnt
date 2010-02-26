 <xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
     <xsl:template match="extension[@point='org.eclipse.ui.newWizards']">
     </xsl:template>
     <xsl:template match="key[@commandId='org.eclipse.ui.window.quickAccess']">
     </xsl:template>
     <xsl:template match="actionSet[@id='org.eclipse.ui.actionSet.keyBindings']">
     </xsl:template>
     <xsl:template match="actionSet[@id='org.eclipse.update.ui.softwareUpdates']">
     </xsl:template>
     <xsl:template match="key[@commandId='org.eclipse.ui.window.showKeyAssist']">
     </xsl:template>
     <xsl:template match="extension[@point='org.eclipse.ui.preferencePages']">
     </xsl:template>
     <xsl:template match="action[@id='org.eclipse.ui.openLocalFile']">
     </xsl:template>
     <xsl:template match="node()|@*">
         <xsl:copy>
             <xsl:apply-templates select="node()|@*"/>
         </xsl:copy>
     </xsl:template>
 </xsl:stylesheet>