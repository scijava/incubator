// From https://stackoverflow.com/a/13066279
// Get a writer to your new file
File f = new File( 'target/test-classes/META-INF/json/org.scijava.plugin.Plugin' )
// Get a reader for the input file
new File( 'target/classes/META-INF/json/org.scijava.plugin.Plugin' ).withReader { r ->

  // And write data from the input into the output
  for (line in r.readLines()) {
    f.append(line);
  }
}
