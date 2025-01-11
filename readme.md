# MindEye - Your Eye into Your Mind

Welcome to **MindEye**, a thoughtfully designed Android application that combines **journaling** and **momentary well-being logging** into a unified experience. Developed as part of a mobile application development course at Commonwealth University Bloomsburg, MindEye simplifies personal mindfulness by providing powerful tools for self-reflection.

## 📌 Features

1. **Log Mental State**  
   - Rate your current mood on a scale from **very unpleasant** to **very pleasant**.  
   - Include optional context by journaling your thoughts.

2. **Dynamic Journaling**  
   - Expandable text fields for comfortable journaling.  
   - Integrated **date and time pickers** to timestamp your entries.

3. **Analytics and Insights**  
   - Visualize your mental state trends with a **Mood Over Time graph**.  
   - Track stats like:
     - Total entries logged.
     - Words written.
     - Days journaled.

4. **Interactive Logs Management**  
   - Edit, update, or delete previous logs easily.  
   - Reset the app to start fresh anytime.

5. **User-Friendly UI**  
   - Smooth navigation with **Material Design 3**.  
   - Intuitive layout with tabs for:
     - Home (Dashboard).  
     - Logs.  
     - Settings.  

6. **Seamless Data Handling**  
   - Backed by an **SQLite database** to store and retrieve data securely.  
   - Logs and insights refresh dynamically for real-time updates.

## 🎨 Design Highlights

1. **Material Design 3**  
   - Tabs for seamless navigation between sections.  
   - Custom typography, floating action buttons, and material card views for a polished interface.

2. **SQLite Database**  
   - Stores logs with fields for mood rating, journal text, and timestamps.  
   - Enables robust data querying for insights and analytics.

## 🛠️ How It Works

1. **Getting Started**  
   - Enter your name and email to create an account.  
   - Begin by logging your current mood with optional journaling.

2. **View and Edit Logs**  
   - Use the **Logs Tab** to review past entries.  
   - Update or delete logs as needed.  

3. **Visualize Trends**  
   - The **Mood Over Time graph** shows your emotional trends.  
   - Set goals using statistics like "days journaled."

4. **Settings and Reset**  
   - Reset all data to start fresh.  
   - Learn about the app or contact support directly from the app.

## 🚀 Technologies Used

- **Android Studio**  
  Built with Kotlin and XML for Android development.
  
- **Material Design 3**  
  Provides modern theming and UI components.
  
- **SQLite Database**  
  Lightweight and efficient storage for logs and stats.

## 📚 Challenges Overcome

1. **Integrating SQLite**  
   - Writing and managing SQL queries for data storage, retrieval, and error handling.  

2. **Fragments and Tabs**  
   - Transitioning from a single-activity structure to a multi-fragment interface with tabs.

3. **Mood Graph Implementation**  
   - Though the X and Y axes couldn't fully reflect mood labels, the graph reliably visualizes trends.

## 🤔 Why MindEye?

MindEye is inspired by a gap in existing tools: separate apps for journaling and mood tracking. By combining these functionalities into one, MindEye makes mindfulness **simpler, faster, and more insightful**.

---

## 📂 App Structure

1. **Home Fragment**  
   - Displays mood trends, stats, and quick access to journaling.

2. **Logs Fragment**  
   - Lists all entries for easy review and editing.

3. **Settings Fragment**  
   - Reset data, learn about the app, or contact support.

---

**MindEye: Reflect, log, and grow — all in one app!**
***Developed by Matthew Yurkunas for educational purposes only***
