# WiseBridge
WiseBridge is an innovative Android application that seamlessly combines Java, XML, and Firebase technologies to bridge the educational gap between students and experts. The primary goal is to facilitate the sharing of high-quality educational content, fostering a mutually beneficial environment where students can access valuable materials while experts are incentivized to contribute.

The application features distinct login/authentication portals for administrators, experts, and students, each employing encryption for secure authorization. Administrators play a crucial role in verifying content and user authenticity, overseeing expert uploads, and managing timely content availability. Experts, on the other hand, contribute educational material and authorize payments from students. This unique model allows students to focus on their studies by monetizing the content, minimizing the need for unrelated part-time jobs.

The user interface, crafted using XML, offers simplicity and user-friendliness. A nested scroll view and recycler view efficiently showcase content, user details, and expert information. Firebase serves as the backbone for database management, storing real-time information about administrators, content users, and experts.

User and expert profiles include essential details such as email, name, encrypted password (using a simple Caesar cipher), and a verification value. Admin verification transforms the initial value of the verification field from 0 to 1, ensuring the authenticity of users and experts. For experts contributing monetized content, additional account information is collected for payment transactions.

Content data encompasses key elements like title, small description, price, subscribers, reviews, verification status, and a storage database URL. The Firebase storage database houses diverse educational content, including PDFs, videos, and more.

In summary, WiseBridge revolutionizes the educational experience by creating a platform that benefits both students and experts. Its robust architecture, secure authentication, and efficient database management make it a valuable asset for those seeking quality educational content and for experts looking to share their knowledge.
