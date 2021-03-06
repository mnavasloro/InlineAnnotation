# InlineAnnotation

This software reads a folder with plain documents, annotates them using the TARSQI toolkit and transforms the output to an inline format. The value atribute is also normalized to a "YYYY-MM-DD" format.

It produces two files per document:
- NAME_OF_THE_DOC.output.xml: the TARSQI output.
- NAME_OF_THE_DOC.output.xml.normalized.xml: the inline version of TARSQI with the change of format for the value atribute of the tag.

Note that is needed to have python installed for TARSQI to run properly (also TreeTaagger and Mallet should be installed to run Tarsqi, and a config.txt file pointing to their folders must be created based on the config.sample.txt file in lib/ttk2.1.0). Also the creation of an environment variable INLINEANNOTATION to the root folder of the project is needed. The folder to process must be an argument.
