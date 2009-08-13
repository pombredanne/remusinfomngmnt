def preset( final path, final value ) {
  featureMap.put(path,value)
}
def setValue (nodeId, final value ) {
  valueMap.put(nodeId, value)
}
def addDynamicValue(nodeId, value) {
	dynamicValues.put(value, nodeId)
}
def addFile (nodeId, final path) {
  fileMap.put(nodeId, path)
}


