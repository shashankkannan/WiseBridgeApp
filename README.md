

About
Locations Java files in GITHUB: \app\src\main\java\com\example\wisebridge XML files: \app\src\main\res\layout

Overview

WiseBridge is an innovative Android application that seamlessly combines Java, XML, and Firebase technologies to bridge the educational gap between students and experts. The primary goal is to facilitate the sharing of high-quality educational content, fostering a mutually beneficial environment where students can access valuable materials while experts are incentivized to contribute.

The application features distinct login/authentication portals for administrators, experts, and students, each employing encryption for secure authorization. Administrators play a crucial role in verifying content and user authenticity, overseeing expert uploads, and managing timely content availability. Experts, on the other hand, contribute educational material and authorize payments from students. This unique model allows students to focus on their studies by monetizing the content, minimizing the need for unrelated part-time jobs.

The user interface, crafted using XML, offers simplicity and user-friendliness. A nested scroll view and recycler view efficiently showcase content, user details, and expert information. Firebase serves as the backbone for database management, storing real-time information about administrators, content users, and experts.

User and expert profiles include essential details such as email, name, encrypted password (using a simple Caesar cipher), and a verification value. Admin verification transforms the initial value of the verification field from 0 to 1, ensuring the authenticity of users and experts. For experts contributing monetized content, additional account information is collected for payment transactions.

Content data encompasses key elements like title, small description, price, subscribers, reviews, verification status, and a storage database URL. The Firebase storage database houses diverse educational content, including PDFs, videos, and more.

In summary, WiseBridge revolutionizes the educational experience by creating a platform that benefits both students and experts. Its robust architecture, secure authentication, and efficient database management make it a valuable asset for those seeking quality educational content and for experts looking to share their knowledge.

Features

Application Flow:
1. Expert Registration and Content Creation ( Login and Registration page)

Experts register using a dedicated registration page providing details like name, email, password, account information, and verifying their identity.After registration, experts log in using their credentials. Logged-in experts can create and upload content. They provide details such as title, description, price, and attach the content file. The verification status of the expert is displayed.

2. Admin Approval Process:

Admin Verification Dashboard: Admins review expert registrations. Admins have a dedicated login with specific credentials. They verify expert details, ensuring accurate information and valid content.They also approve or reject content uploaded by experts based on quality, adherence to guidelines, and appropriateness.
3. User Registration and Access:

Users register by providing essential details like name, email, and password.  Registered users log in securely using their credentials. Users can browse through a RecyclerView displaying a list of available content. Free content is accessible immediately, while paid content requires a purchase.

4. Purchasing Paid Content: 

Users choose paid content, initiate the payment process, and complete the transaction securely. Payment gateways or in-app purchases handle the payment. Upon successful payment, users receive a verification code.

Payment Page: Users enter payment details such as verification code to access premium content to facilitate smooth transactions.
Verification Page: This code is submitted to the respective expert for approval, ensuring a secure and controlled access mechanism.
5. Expert Verification and Content Access:

Users submit the verification code to the respective expert for access to paid content. The expert reviews the code and approves it if valid. Once approved, users can download the paid content securely from Firebase Storage.

6. Review content

Users who have accessed a particular piece of content can navigate to the Review Page to share their opinions. This creates a sense of community engagement and provides valuable insights for both content creators and potential consumers. Each review is displayed prominently on the Review Page, showcasing the user’s comments, and rating. The average rating for each content item is calculated based on the reviews received from users. is prominently displayed on the Content Details Page, allowing users to quickly gauge the overall satisfaction level of their peers.


Getting Started
Clone the repository:
Use \`git clone https://github.com/your-username/WiseBridge-Android.git\` to clone the repository.

