;;;
;;; .emacs -- Control file for GNU Emacs startup
;;;
;;; $Id:$
;;;
;;;


;;
;; Set the load path:
;; ------------------
;;
(setq load-path
    (append load-path (list
      "c:/software/emacs-21.3/ext/jde-2.3.2/lisp" 
      "c:/software/emacs-21.3/ext/elib-1.0" 
      "c:/software/emacs-21.3/ext/eieio-0.1.7" 
      "c:/software/emacs-21.3/ext/semantic-1.4.4" 
      "c:/software/emacs-21.3/ext/speedbar-0.1.4-b4")))

;;  (global-set-key "\C-cld" 'tempo-template-java-log4j-debug)
;;  (global-set-key "\C-cli" 'tempo-template-java-log4j-info)
;;  (global-set-key "\C-clw" 'tempo-template-java-log4j-warn)
;;  (global-set-key "\C-cle" 'tempo-template-java-log4j-error)
;;  (tempo-define-template "java-log4j-debug" '(& "LOG.debug(\"" p "\");" >))
;;  (tempo-define-template "java-log4j-info"  '(& "LOG.info(\"" p "\");" >))
;;  (tempo-define-template "java-log4j-warn"  '(& "LOG.warn(\"" p "\");" >))
;;  (tempo-define-template "java-log4j-error" '(& "LOG.error(\"" p "\");" >))

;;
;; Set tag table list:
;; -------------------
;;
(setq tags-table-list '(
  "d:/dev/apps/puma/src"))


;;
;; Generic Global initializations:
;; -------------------------------
;;
;;(menu-bar-mode nil)
(setq scroll-step 1)
(setq inhibit-startup-message t)
(setq-default scroll-bar-mode -1)
(setq-default c-tab-always-indent nil)
(setq-default tab-width 4)
(put 'eval-expression 'disabled nil)


;;
;; Color customizations:
;; ---------------------
;;
(set-background-color "gray82")
;;(set-face-background 'modeline "gray0")
;;(set-face-foreground 'modeline "firebrick2")
;;(set-face-background 'highlight "midnightblue")
;;(set-face-foreground 'highlight "white")
;;(set-face-background 'region "midnightblue")
;;(set-face-foreground 'region "white")
(set-cursor-color "red1")


;;
;; Custom command functions:
;; -------------------------
;;

(defun move-point-to-top()
    (interactive) (move-to-window-line 0))

(defun move-point-to-bottom()
    (interactive) (move-to-window-line -1))

(defun scroll-ahead (&optional n)
    (interactive "P") (scroll-up (prefix-numeric-value n)))

(defun scroll-behind (&optional n)
    (interactive "P") (scroll-down (prefix-numeric-value n)))

;;(defun compile-fg()
;;  (interactive "sCompile command: make")
;;  (compile))
;;  (delete-window)
;;  (end-of-buffer))



;;
;; Custom key-bindings:
;; --------------------
;;

;;(global-set-key "\C-?" 'delete-char)
(global-set-key "\M-?" 'help-command)
(global-set-key "\C-h" 'delete-backward-char)
(global-set-key [C-backspace] 'backward-kill-word)
(global-set-key "\C-x\C-e" 'compile)
(global-set-key "\C-ce" 'eval-expression)
(global-set-key [f9] 'next-error)
(global-set-key [f10] 'previous-error)


;;
;; Cursor movement key-bindings:
;; -----------------------------
;;
(global-set-key [end] 'end-of-line)
(global-set-key [home] 'beginning-of-line)
(global-set-key [C-end] 'end-of-buffer)
(global-set-key [C-home] 'beginning-of-buffer)
(global-set-key "\C-xg" 'goto-line)
(global-set-key "\C-V" 'scroll-ahead)
(global-set-key "\C-Z" 'scroll-behind)
(global-set-key "\M-h" 'move-point-to-top)
(global-set-key "\M-l" 'move-point-to-bottom)
(global-set-key "\C-O" 'other-window)
(global-set-key [f4] 'tags-query-replace)
;;(global-set-key "\C-x\C-e" 'compile-fg)


;;
;; custom mode hooks:
;; ------------------
;;

(defun my-load-dired-x() (require 'dired-x))
(add-hook 'dired-mode-hook 'my-load-dired-x)

(load "complete")
;;(require 'complete)
;;(setq partial-completion-mode t)
(partial-completion-mode)

(defun dired-find-files ()
  "Visit the marked files."
  (interactive)
  (let ((files (reverse (dired-get-marked-files))))
  ;;(let ((files (dired-get-marked-files)))
    (while files
      (find-file (car files))
      (setq files (cdr files)))))

(defun string-to-char-string-list (a)
  (if (string= a "") '("$")
    (cons (substring a 0 1) (string-to-char-string-list (substring a 1)))))
    
;; convert shell regexps to emacs regexps (naive!)
(defun regexp-conv (a) 
  (mapconcat 'regexp-convert (string-to-char-string-list  a) ""))
    
(defun regexp-convert (a)
  (cond 
    ((string= a "*") ".*" )
    ((string= a ".") "\\.")
    ((string= a "?") ".")
    ('t a)))
    
(defun find-many-files (&optional fmfregexp)
  "loads a lot of files matching a regexp"
  (interactive)
  (let ((or-regexp  
    (if fmfregexp fmfregexp 
      (let ((fmf-input (read-file-name "Open File(s) regexp: ")))
        (concat (file-name-directory fmf-input)
    	 (regexp-conv (file-name-nondirectory fmf-input)))))))
    (mapcar 'find-file 
      (directory-files 
    (file-name-directory or-regexp) 't 
    (file-name-nondirectory or-regexp)))
    (list-buffers 't)
    (message "done!")))


;;
;; JDE Initialization:
;; -------------------
;;

(load "jde")
(load "jde-ant")

(defun java-compile()
    (interactive) (jde-compile))
(defun java-make()
    (interactive) (jde-make))
(defun java-run()
    (interactive) (jde-run-set-app "startup") (jde-run))

(defun jde-custom-compile()
    (interactive) (setq buildfile (jde-ant-interactive-get-buildfile)) (jde-ant-build buildfile nil nil))
(defun jde-custom-run()
    (interactive) (setq buildfile (jde-ant-interactive-get-buildfile)) (jde-ant-build buildfile "run" nil))


;;
;; Configure mode for JSP:
;; -----------------------
;;

;;(load "jsp")
;;(autoload 'jsp-mode "jsp" "JSP" t)
;;(add-to-list 'auto-mode-alist '("\\.jsp$'" . html-mode))
;;(setq auto-mode-alist (cons '("\\.jsp$" . html-mode) auto-mode-alist))


;;
;; global customization:
;; ---------------------
;;

(custom-set-variables
  ;; custom-set-variables was added by Custom -- don't edit or cut/paste it!
  ;; Your init file should contain only one such instance.
 '(archive-zip-use-pkzip t)
 '(c-basic-offset 4)
 '(case-fold-search t)
 '(current-language-environment "Latin-1")
 '(default-input-method "latin-1-prefix")
 '(display-time-mode t nil (time))
 '(font-lock-maximum-size 512000)
 '(global-font-lock-mode t nil (font-lock))
 '(global-semantic-auto-parse-mode nil nil (semantic-util-modes))
 '(global-semantic-show-dirty-mode nil nil (semantic-util-modes))
 '(global-semantic-show-unmatched-syntax-mode nil nil (semantic-util-modes))
 '(global-semantic-summary-mode nil nil (semantic-util-modes))
 '(global-senator-minor-mode nil nil (senator))
 '(hscroll-global-mode t nil (hscroll))
 '(hscroll-margin 4)
 '(hscroll-mode-name " wide")
 '(indent-tabs-mode nil)
 '(jde-ant-args "-emacs")
 '(jde-ant-build-hook (quote (jde-compile-finish-refresh-speedbar jde-compile-finish-flush-completion-cache)))
 '(jde-ant-buildfile "/dev/apps/puma/bin/build.xml")
 '(jde-ant-enable-find nil)
 '(jde-ant-home "/software/apache-ant-1.7.0")
 '(jde-ant-read-buildfile nil)
 '(jde-ant-read-target t)
 '(jde-compile-option-classpath nil)
 '(jde-enable-abbrev-mode nil)
 '(jde-gen-cflow-enable nil)
 '(jde-imenu-include-modifiers t)
 '(jde-key-bindings (list (cons "[? ? ?]" (quote jde-run-menu-run-applet)) (cons "[? ? ?]" (quote jde-build)) (cons "[? ? ?]" (quote jde-custom-compile)) (cons "[? ? ?]" (quote jde-debug)) (cons "[? ? ?]" (quote jde-find)) (cons "[? ? ?]" (quote jde-open-class-at-point)) (cons "[? ? ?]" (quote bsh)) (cons "[? ? ?]" (quote jde-gen-println)) (cons "[? ? ?]" (quote jde-help-browse-jdk-doc)) (cons "[? ? ?]" (quote jde-save-project)) (cons "[? ? ?]" (quote jde-wiz-update-class-list)) (cons "[? ? ?]" (quote jde-custom-run)) (cons "[? ? ?]" (quote speedbar-frame-mode)) (cons "[? ? ?]" (quote jde-db-menu-debug-applet)) (cons "[? ? ?]" (quote jde-help-symbol)) (cons "[? ? ?]" (quote jde-show-superclass-source)) (cons "[? ? ?]" (quote jde-open-class-at-point)) (cons "[? ? ?]" (quote jde-import-find-and-import)) (cons "[? ? ?e]" (quote jde-wiz-extend-abstract-class)) (cons "[? ? ?f]" (quote jde-gen-try-finally-wrapper)) (cons "[? ? ?i]" (quote jde-wiz-implement-interface)) (cons "[? ? ?j]" (quote jde-javadoc-autodoc-at-line)) (cons "[? ? ?o]" (quote jde-wiz-override-method)) (cons "[? ? ?t]" (quote jde-gen-try-catch-wrapper)) (cons "[? ? ?]" (quote jde-run-etrace-prev)) (cons "[? ? ?]" (quote jde-run-etrace-next)) (cons "[(control c) (control v) (control ?.)]" (quote jde-complete)) (cons "[(control c) (control v) ?.]" (quote jde-complete-in-line))))
 '(jde-project-file-name "project.el")
 '(save-place nil nil (saveplace))
 '(semanticdb-global-mode nil nil (semanticdb))
 '(show-paren-mode t nil (paren))
 '(standard-indent 4)
 '(transient-mark-mode t)
 '(which-func-mode-global t nil (which-func))
 '(which-function-mode nil nil (which-func)))
(custom-set-faces
  ;; custom-set-faces was added by Custom -- don't edit or cut/paste it!
  ;; Your init file should contain only one such instance.
 '(default ((t (:bold nil :italic nil :underline nil :foreground "black" :background "gray82"))))
 '(bold ((t (:bold t :foreground "black" :background "gray82"))))
 '(bold-italic ((t (:bold t :italic t :foreground "black" :background "gray82"))))
 '(border ((t (:background "black"))))
 '(font-lock-builtin-face ((((class color) (background light)) (:bold t :foreground "Navy" :background "gray82"))))
 '(font-lock-comment-face ((((class color) (background light)) (:bold t :foreground "DarkGreen" :background "gray82"))))
 '(font-lock-constant-face ((((class color) (background light)) (:bold t :foreground "DarkBlue" :background "gray82"))))
 '(font-lock-function-name-face ((((class color) (background light)) (:bold t :underline t :foreground "Navy" :background "gray82"))))
 '(font-lock-keyword-face ((((class color) (background light)) (:bold t :foreground "Blue" :background "gray82"))))
 '(font-lock-string-face ((((class color) (background light)) (:foreground "Blue" :background "gray82"))))
 '(font-lock-type-face ((((class color) (background light)) (:foreground "Blue" :background "gray82"))))
 '(font-lock-variable-name-face ((((class color) (background light)) (:foreground "Black" :background "gray82"))))
 '(font-lock-warning-face ((((class color) (background light)) (:bold t :foreground "Red" :background "Gray82"))))
 '(fringe ((t (:background "#bbbbff" :box (:line-width 2 :color "grey75" :style released-button)))))
 '(header-line ((((class color grayscale) (background light)) (:inherit mode-line :background "grey90" :foreground "grey20" :box (:line-width 1 :color "grey75" :style released-button)))))
 '(highlight ((((class color) (background light)) (:background "#bbbbff"))))
 '(jde-java-font-lock-code-face ((t (:foreground "black" :weight bold))))
 '(jde-java-font-lock-constant-face ((((class color) (background light)) (:foreground "black" :weight bold))))
 '(jde-java-font-lock-doc-tag-face ((((class color) (background light)) (:foreground "black" :weight bold))))
 '(jde-java-font-lock-italic-face ((t (:slant italic))))
 '(jde-java-font-lock-modifier-face ((((class color) (background light)) (:foreground "blue" :weight bold))))
 '(jde-java-font-lock-number-face ((((class color) (background light)) (:foreground "midnightblue" :weight bold))))
 '(jde-java-font-lock-operator-face ((((class color)) (:foreground "medium blue" :weight bold))))
 '(jde-java-font-lock-package-face ((((class color) (background light)) (:foreground "midnightblue"))))
 '(jde-java-font-lock-pre-face ((t (:foreground "black" :weight bold))))
 '(mode-line ((t (:background "#ccccff" :box (:line-width 2 :color "#cccccc" :style released-button)))))
 '(mouse ((t (:background "black"))))
 '(region ((t (:background "#aaaaff" :foreground "black"))))
 '(show-paren-mismatch-face ((((class color)) (:foreground "black" :background "green"))))
 '(speedbar-button-face ((t (:foreground "midnightblue" :weight bold))))
 '(speedbar-directory-face ((t (:foreground "midnightblue" :weight bold))))
 '(speedbar-file-face ((t (:foreground "black"))))
 '(speedbar-highlight-face ((t (:background "#9999ff" :underline t :weight bold))))
 '(speedbar-selected-face ((t (:foreground "blue" :underline t :weight bold))))
 '(speedbar-tag-face ((((class color) (background light)) (:foreground "blue"))))
 '(tool-bar ((((type x w32 mac) (class color)) (:background "grey75" :foreground "red" :box (:line-width 1 :style released-button)))))
 '(vhdl-font-lock-prompt-face ((((class color) (background light)) (:bold nil :foreground "SpringGreen"))))
 '(vhdl-font-lock-value-face ((((class color) (background light)) (:foreground "Blue")))))

(put 'upcase-region 'disabled nil)
(put 'downcase-region 'disabled nil)

