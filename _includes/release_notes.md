v.0.9 Maintenance release with small improvements:

- Bug fixes and code improvements
- Cell lines created in MIRACLE can now be linked to cell lines managed in [OpenLabFramework](https://github.com/NanoCAN/OpenLabFramework). Therefore two new lines need to be added to the configuration file:
  - ```openlabframework.rest.url = "https://localhost/OpenLabFramework```
  Change the URL to a running OpenLabFramework instance that supports https, e.g. https://130.225.157.237/OpenLabFramework.
  - ```openlabframework.appAccessToken = "xxx"```
  See [OpenLabFramework guide](https://github.com/NanoCAN/OpenLabFramework/wiki/AppAccessTokens) to learn how to obtain an app access token.

- Fixed problems with creating plate layout copies
- Renamed NumberOfCellsSeeded to Amount of protein / Number of cells seeded
- Implemented new feature that allows copying of slide layouts
- A note is now shown if a slide contains block shifts
- Block shifts can now be deleted
- A javascript based heatmap has been implemented to reduce MIRACLE's dependency on R for data management. Since the new heatmap is interactive ,individual samples can be quickly identified by moving the mouse cursor to the corresponding spot. Note that the heatmap for the block shifts still relies on R shiny since the block shifts are corrected on the R side.

v.0.8 First public release, together with the MIRACLE [ECCB paper](http://www.ncbi.nlm.nih.gov/pubmed/25161257)
