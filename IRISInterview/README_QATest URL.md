# IRISinterview

Issue: Page is broken and body of the page is not visible.

URL: https://osa-web.t-cg.co.uk/qatest

Environment details: QA

Description: 
When user tries to open the URL, it's not loading properly and unable to see the body of the web page:
Steps to reproduce:
Pre-requsities: Launch the QA URL
Actual: User is not displayed the fully page.
Actual: User should be displayed the full page.

Root Cause: loading issue

Root Cause Description: Page is not loading completly for Body section of the page.

How I analyzed: I used Inspection in the page and tried finding the difference with news page, so that i got the problem that is loading. There are some mismatches with tags and values as per header in QAtest and the body contains "Loading..." instead of  "finshedLoading". Once the tags and UI is updated, it may work.

Note: I found another issue in the main menu, that Mail option is there but not has any value in UI, so i just added the value  as "Email Us" in the dev page. Please refer the screenshot "IRIS_Mail issue".