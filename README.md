# MVWUpdater

This software is designed to be an easy to use and highly customizable updater application for software.

To use it, get started by downloading the latest release from the [releases](https://github.com/SteveOhIo/MVWUpdater/releases) tab.

To configure the updater, simply open up the included **config.toml** file.

MVWUpdater works by downloading files from static download links. Dropbox is highly recommended for this, as you can upload a file called "latest.txt", get a link to that file, and then modify the dropbox.com part to be dl.dropboxusercontent.com. This will provide a static link to your latest version info. The same can be done for a .7z latest.7z file containing the actual program files, and a changelog.txt file.

Each line of the changelog file will be treated as a bullet point.
