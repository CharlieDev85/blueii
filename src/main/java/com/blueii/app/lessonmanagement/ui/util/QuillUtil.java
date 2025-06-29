package com.blueii.app.lessonmanagement.ui.util;

import com.vaadin.flow.component.UI;

public class QuillUtil {
/**
        * Initializes a Quill editor inside the element with the given ID.
     *
             * @param containerId The DOM ID of the div where Quill should initialize.
     */
    public static void initializeQuill(String containerId) {
        UI.getCurrent().getPage().executeJs("""
            if (!window.quillLoaded) {
                const cssId = 'quill-snow-css';
                if (!document.getElementById(cssId)) {
                    const link = document.createElement('link');
                    link.id = cssId;
                    link.rel = 'stylesheet';
                    link.href = 'https://cdn.jsdelivr.net/npm/quill@2.0.3/dist/quill.snow.css';
                    document.head.appendChild(link);
                }
                const script = document.createElement('script');
                script.src = 'https://cdn.jsdelivr.net/npm/quill@2.0.3/dist/quill.js';
                script.onload = () => {
                    window.quillLoaded = true;
                    initQuill($0);
                };
                document.head.appendChild(script);
            } else {
                initQuill($0);
            }

            function initQuill(containerId) {
                const container = document.getElementById(containerId);
                if (container && !container.classList.contains('ql-container')) {
                    container.innerHTML = '';
                    new Quill(container, { theme: 'snow' });
                }
            }
        """, containerId);
    }
}
