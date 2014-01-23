$(document).ready(function() {
  
  ko.punches.enableAll();
  
  // Initially load all tasks
  loadTasks();
  
  /////////////////////////////////////////////////////////////////////////////
  // TASK                                                                    //
  /////////////////////////////////////////////////////////////////////////////
  
  /**
   * View model.
   */
  function TaskModel() {
    var self = this;
    
    self.selectedTask = ko.observable();
    self.tasks = ko.observableArray();
    
    self.selectTask = function(data) {
      self.selectedTask(data);

      // load all notes of selected task
      loadNotes();
      
      // load all links of selected task
      loadLinks();
      
      // load all file of selected task
      loadFiles();

    };
    
    self.newTask = function(data) {
      $('#new-task-button').toggle();
      $('#new-task-form').fadeToggle("fast");
    };
    
    self.cancelNewTask = function(data) {
      $('#new-task-button').toggle();
      $('#new-task-form').toggle();
      $('#new-task-form input').val(null);
    };
    
    self.addTask = function(data) {
      addTask(data);
//      $('#new-note-button').toggle();
//      $('#new-note-form').toggle();
    };
    
    self.editTask = function(data) {
//      $('#new-note-button').toggle();
//      $('#new-note-form').toggle();
      $('#'+data.idAsString+' .inline-view').toggle();
      $('#'+data.idAsString+' .inline-edit').toggle();
    };

    self.cancelEditTask = function(data) {
      $('#'+data.idAsString+' .inline-view').toggle();
      $('#'+data.idAsString+' .inline-edit').toggle();
    };
    
    self.saveTask = function(data) {
      saveTask(data);
    };
    
    self.deleteTask = function(data) {
      deleteTask(data);
    };
    
    // --
    
    self.notes = ko.observableArray();
    
    self.newNote = function(data) {
      $('#new-note-button').toggle();
      $('#new-note-form').fadeToggle("fast");
    };
    
    self.cancelNewNote = function(data) {
      $('#new-note-button').toggle();
      $('#new-note-form').toggle();
      $('#new-note-form textarea').val(null);
    };
    
    self.addNote = function(data) {
      addNote(data);
      $('#new-note-button').toggle();
      $('#new-note-form').toggle();
    };
    
    self.editNote = function(data) {
      $('#new-note-button').toggle();
      $('#new-note-form').toggle();
      $('#'+data.idAsString+' .inline-view').toggle();
      $('#'+data.idAsString+' .inline-edit').toggle();
    };

    self.cancelEditNote = function(data) {
      $('#'+data.idAsString+' .inline-view').toggle();
      $('#'+data.idAsString+' .inline-edit').toggle();
    };
    
    self.saveNote = function(data) {
      saveNote(data);
    };
    
    self.deleteNote = function(data) {
      deleteNote(data);
    };
    
    // --
    
    self.links = ko.observableArray();
    
    self.newLink = function(data) {
      $('#new-link-button').toggle();
      $('#new-link-form').fadeToggle("fast");
    };
    
    self.cancelNewLink = function(data) {
      $('#new-link-button').toggle();
      $('#new-link-form').toggle();
      $('#new-link-form input').val(null);
      $('#new-link-form textarea').val(null);
    };
    
    self.addLink = function(data) {
      addLink(data);
      $('#new-link-button').toggle();
      $('#new-link-form').toggle();
    };
    
    self.editLink = function(data) {
      $('#new-link-button').toggle();
      $('#new-link-form').toggle();
      $('#'+data.idAsString+' .inline-view').toggle();
      $('#'+data.idAsString+' .inline-edit').toggle();
    };

    self.cancelEditLink = function(data) {
      $('#'+data.idAsString+' .inline-view').toggle();
      $('#'+data.idAsString+' .inline-edit').toggle();
    };
    
    self.saveLink = function(data) {
      saveLink(data);
    };
    
    self.deleteLink = function(data) {
      deleteLink(data);
    };
    
    // --
    
    self.files = ko.observableArray();
    
//    self.newFile = function(data) {
//      $('#new-file-button').toggle();
//      $('#new-file-form').fadeToggle("fast");
//    };
//    
//    self.cancelNewFile = function(data) {
//      $('#new-file-button').toggle();
//      $('#new-file-form').toggle();
//      $('#new-file-form input').val(null);
//      $('#new-file-form textarea').val(null);
//    };
//    
    self.addFile = function(data) {
      addFile();
    };
//    
//    self.editFile = function(data) {
//      $('#new-file-button').toggle();
//      $('#new-file-form').toggle();
//      $('#'+data.idAsString+' .inline-view').toggle();
//      $('#'+data.idAsString+' .inline-edit').toggle();
//    };
//
//    self.cancelEditFile = function(data) {
//      $('#'+data.idAsString+' .inline-view').toggle();
//      $('#'+data.idAsString+' .inline-edit').toggle();
//    };
//    
//    self.saveFile = function(data) {
//      saveFile(data);
//    };
    
    self.deleteFile = function(data) {
      deleteFile(data);
    };
  };
  
  // Apply view model
  var taskModel = new TaskModel();
  ko.applyBindings(taskModel, document.getElementById("tasks"));
  
  /**
   * Load all tasks.
   */
  function loadTasks() {
    $.ajax({
      url: "/taskstodo/tasks/api/list/",
      type: "GET",

      beforeSend: function(xhr) {
        xhr.setRequestHeader("Accept", "application/json");
        xhr.setRequestHeader("Content-Type", "application/json");
      },
      success: function(data) {
        if (data != null) {
          taskModel.tasks.removeAll();
          ko.utils.arrayForEach(data, function(item){
            taskModel.tasks.push(item);
          });
        }
      },
      error: function(jqXHR, textStatus, errorThrown) {
        if (console && console.error) {
          console.error("Error loading the tasks! Error: " + errorThrown);
        }
      }
    });
  }
  
  /**
   * Adds a new task.
   */
  function addTask(form) {
    var taskTitle = $("#input-new-task-title").val();
    
    if (taskTitle != null && taskTitle.trim().length > 0) {
      var json = { "title" : taskTitle };
      
      $.ajax({
        url: "/taskstodo/tasks/api/create",
        data: JSON.stringify(json),
        type: "POST",
        
        beforeSend: function(xhr) {
          xhr.setRequestHeader("Accept", "application/json");
          xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function(data) {
          if (data != null) {
            taskModel.tasks.unshift(data);
          }
          
          // Reset the form
          $('#input-new-task-title').val(null);
        },
        error: function(jqXHR, textStatus, errorThrown) {
          if (console && console.error) {
            console.error("Error adding new task! Error: " + errorThrown);
          }
        }
      });
    }
  }
  
  /**
   * Saves changes to a task.
   */
  function saveTask(task) {
    if (task != null) {
      var json = { "title" : task.title };
      
      $.ajax({
        url: "/taskstodo/tasks/api/update/" + task.idAsString,
        data: JSON.stringify(json),
        type: "PUT",
        
        beforeSend: function(xhr) {
          xhr.setRequestHeader("Accept", "application/json");
          xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function(data) {
          if (console && console.log) {
            console.log("Task " + task.idAsString + " successfully updated!");
          }
          
          loadTasks();
          // TODO Deactivate edit mode of this task!
        },
        error: function(jqXHR, textStatus, errorThrown) {
          if (console && console.error) {
            console.error("Error updating task " + task.idAsString + "! Error: " + errorThrown + " " + jqXHR.responseText);
          }
        }
      });
    }
  }
  
  /**
   * Deletes a given task.
   */
  function deleteTask(task) {
    $.ajax({
      url: "/taskstodo/tasks/api/delete/" + task.idAsString,
      type: "DELETE",

      beforeSend: function(xhr) {
        xhr.setRequestHeader("Accept", "application/json");
        xhr.setRequestHeader("Content-Type", "application/json");
      },
      success: function(data) {
        if (console && console.log) {
          console.log("Task " + task.idAsString + " successfully deleted!");
        }
        
        // Remove the task from the view model
        taskModel.tasks.remove(task);
      },
      error: function(jqXHR, textStatus, errorThrown) {
        if (console && console.error) {
          console.error("Error deleting task " + task.idAsString + "! Error: " + errorThrown);
        }
      }
    });
  }
  
  /////////////////////////////////////////////////////////////////////////////
  // TASK: NOTES                                                             //
  /////////////////////////////////////////////////////////////////////////////
  
  /**
   * Load all notes regarding to the selected task.
   */
  function loadNotes() {
    $.ajax({
      url: "/taskstodo/notes/api/list/"+taskModel.selectedTask().idAsString,
      type: "GET",

      beforeSend: function(xhr) {
        xhr.setRequestHeader("Accept", "application/json");
        xhr.setRequestHeader("Content-Type", "application/json");
      },
      success: function(data) {
        if (data != null) {
          taskModel.notes.removeAll();
          
          ko.utils.arrayForEach(data, function(item){
            taskModel.notes.push(item);
          });
        }
      },
      error: function(jqXHR, textStatus, errorThrown) {
        if (console && console.error) {
          console.error("Error loading the task notes! Error: " + errorThrown);
        }
      }
    });
  }
  
  /**
   * Adds a new note to the task.
   */
  function addNote(form) {
    var note = $("#input-new-note-body").val();
    
    if (note != null && note.trim().length > 0) {
      var json = { "body" : $("#input-new-note-body").val() };
      
      $.ajax({
        url: "/taskstodo/notes/api/create/"+taskModel.selectedTask().idAsString,
        data: JSON.stringify(json),
        type: "POST",
        
        beforeSend: function(xhr) {
          xhr.setRequestHeader("Accept", "application/json");
          xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function(data) {
          if (data != null) {
            taskModel.notes.unshift(data);
          }
          
          // Reset the form
          $('#input-new-note-body').val(null);
        },
        error: function(jqXHR, textStatus, errorThrown) {
          if (console && console.error) {
            console.error("Error adding new note! Error: " + errorThrown);
          }
        }
      });
    }
  }
  
  /**
   * Saves changes to a note.
   */
  function saveNote(note) {
    if (note != null && note.body != null && note.body.trim().length > 0) {
      var json = { "body" : note.body };
      
      $.ajax({
        url: "/taskstodo/notes/api/update/" + note.idAsString,
        data: JSON.stringify(json),
        type: "PUT",
        
        beforeSend: function(xhr) {
          xhr.setRequestHeader("Accept", "application/json");
          xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function(data) {
          if (console && console.log) {
            console.log("Note " + note.idAsString + " successfully updated!");
          }
          
          loadNotes();
          // TODO Deactivate edit mode of this note!
        },
        error: function(jqXHR, textStatus, errorThrown) {
          if (console && console.error) {
            console.error("Error updating note " + note.idAsString + "! Error: " + errorThrown + " " + jqXHR.responseText);
          }
        }
      });
    }
  }
  
  /**
   * Deletes a given note.
   */
  function deleteNote(note) {
    $.ajax({
      url: "/taskstodo/notes/api/delete/" + note.idAsString,
      type: "DELETE",

      beforeSend: function(xhr) {
        xhr.setRequestHeader("Accept", "application/json");
        xhr.setRequestHeader("Content-Type", "application/json");
      },
      success: function(data) {
        if (console && console.log) {
          console.log("Note " + note.idAsString + " successfully deleted!");
        }
        
        // Remove the note from the view model
        taskModel.notes.remove(note);
      },
      error: function(jqXHR, textStatus, errorThrown) {
        if (console && console.error) {
          console.error("Error deleting note " + note.idAsString + "! Error: " + errorThrown);
        }
      }
    });
  }
  
  /////////////////////////////////////////////////////////////////////////////
  // TASK: LINKS                                                             //
  /////////////////////////////////////////////////////////////////////////////

  /**
   * Load all links regarding to the selected task.
   */
  function loadLinks() {
    $.ajax({
      url: "/taskstodo/links/api/list/"+taskModel.selectedTask().idAsString,
      type: "GET",

      beforeSend: function(xhr) {
        xhr.setRequestHeader("Accept", "application/json");
        xhr.setRequestHeader("Content-Type", "application/json");
      },
      success: function(data) {
        if (data != null) {
          taskModel.links.removeAll();
          
          ko.utils.arrayForEach(data, function(item) {
            taskModel.links.push(item);
          });
        }
      },
      error: function(jqXHR, textStatus, errorThrown) {
        if (console && console.error) {
          console.error("Error loading links! Error: " + errorThrown);
        }
      }
    });
  }
  
  /**
   * Adds a new note to the task.
   */
  function addLink(form) {
    var url = $("#input-new-link-url").val();
    var title = $("#input-new-link-title").val();
    var description = $("#input-new-link-description").val();
    
    if (url != null && url.trim().length > 0 && title != null && title.trim().length > 0) {
      var json = { "url" : url, "title" : title, "description" : description };
      
      $.ajax({
        url: "/taskstodo/links/api/create/"+taskModel.selectedTask().idAsString,
        data: JSON.stringify(json),
        type: "POST",
        
        beforeSend: function(xhr) {
          xhr.setRequestHeader("Accept", "application/json");
          xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function(data) {
          if (data != null) {
            taskModel.links.unshift(data);
          }
          
          // Reset the form
          $('#new-link-form input').val(null);
          $('#new-link-form textarea').val(null);
        },
        error: function(jqXHR, textStatus, errorThrown) {
          if (console && console.error) {
            console.error("Error adding new link! Error: " + errorThrown);
          }
        }
      });
    }
  }

  /**
   * Saves changes to a link.
   */
  function saveLink(link) {
    var url = link.url;
    var title = link.title;
    var description = link.description;
    
    if (url != null && url.trim().length > 0) {
      var json = { "url" : url, "title" : title, "description" : description };
    
      $.ajax({
        url: "/taskstodo/links/api/update/" + link.idAsString,
        data: JSON.stringify(json),
        type: "PUT",
  
        beforeSend: function(xhr) {
          xhr.setRequestHeader("Accept", "application/json");
          xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function(data) {
          if (console && console.log) {
            console.log("Link " + link.idAsString + " successfully updated!");
          }
          
          loadLinks();
          // TODO Deactivate edit mode of this note!
        },
        error: function(jqXHR, textStatus, errorThrown) {
          if (console && console.error) {
            console.error("Error updating link " + link.idAsString + "! Error: " + errorThrown + " " + jqXHR.responseText);
          }
        }
      });
    }
  }
  
  /**
   * Deletes a given link.
   */
  function deleteLink(link) {
    $.ajax({
      url: "/taskstodo/links/api/delete/" + link.idAsString,
      type: "DELETE",

      beforeSend: function(xhr) {
        xhr.setRequestHeader("Accept", "application/json");
        xhr.setRequestHeader("Content-Type", "application/json");
      },
      success: function(data) {
        if (console && console.log) {
          console.log("Link " + link.idAsString + " successfully deleted!");
        }
        
        // Remove the link from the view model
        taskModel.links.remove(link);
      },
      error: function(jqXHR, textStatus, errorThrown) {
        if (console && console.error) {
          console.error("Error deleting link " + link.idAsString + "! Error: " + errorThrown);
        }
      }
    });
  }
  
  /**
   * TODO
   */
  $('#input-link-url').change(function() {
    var url = $('#input-link-url').val();
    
    // TODO: CALL JAVA API HERE TO GET THE TITLE AND A SCREENSHOT OF THAT URL!
  });
  
  /////////////////////////////////////////////////////////////////////////////
  // TASK: FILES                                                             //
  /////////////////////////////////////////////////////////////////////////////
    
  /**
   * Load all files regarding to the selected task.
   */
  function loadFiles() {
    $.ajax({
      url: "/taskstodo/files/api/list/"+taskModel.selectedTask().idAsString,
      type: "GET",

      beforeSend: function(xhr) {
        xhr.setRequestHeader("Accept", "application/json");
        xhr.setRequestHeader("Content-Type", "application/json");
      },
      success: function(data) {
        if (data != null) {
          taskModel.files.removeAll();
          
          ko.utils.arrayForEach(data, function(item){
            taskModel.files.push(item);
          });
        }
      },
      error: function(jqXHR, textStatus, errorThrown) {
        if (console && console.error) {
          console.error("Error loading files! Error: " + errorThrown);
        }
      }
    });
  }
  

//  /*jslint unparam: true, regexp: true */
//  /*global window, $ */
//  $(function () {
//      'use strict';
//      // Change this to the location of your server-side upload handler:
//      var url = window.location.hostname === 'blueimp.github.io' ?
//                  '//jquery-file-upload.appspot.com/' : 'server/php/',
//          uploadButton = $('<button/>')
//              .addClass('btn btn-primary')
//              .prop('disabled', true)
//              .text('Processing...')
//              .on('click', function () {
//                  var $this = $(this),
//                      data = $this.data();
//                  $this
//                      .off('click')
//                      .text('Abort')
//                      .on('click', function () {
//                          $this.remove();
//                          data.abort();
//                      });
//                  data.submit().always(function () {
//                      $this.remove();
//                  });
//              });
//      $('#fileupload').fileupload({
//          url: url,
//          dataType: 'json',
//          autoUpload: false,
//          acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
//          maxFileSize: 5000000, // 5 MB
//          // Enable image resizing, except for Android and Opera,
//          // which actually support image resizing, but fail to
//          // send Blob objects via XHR requests:
//          disableImageResize: /Android(?!.*Chrome)|Opera/
//              .test(window.navigator.userAgent),
//          previewMaxWidth: 100,
//          previewMaxHeight: 100,
//          previewCrop: true
//      }).on('fileuploadadd', function (e, data) {
//          data.context = $('<div/>').appendTo('#files');
//          $.each(data.files, function (index, file) {
//              var node = $('<p/>')
//                      .append($('<span/>').text(file.name));
//              if (!index) {
//                  node
//                      .append('<br>')
//                      .append(uploadButton.clone(true).data(data));
//              }
//              node.appendTo(data.context);
//          });
//      }).on('fileuploadprocessalways', function (e, data) {
//          var index = data.index,
//              file = data.files[index],
//              node = $(data.context.children()[index]);
//          if (file.preview) {
//              node
//                  .prepend('<br>')
//                  .prepend(file.preview);
//          }
//          if (file.error) {
//              node
//                  .append('<br>')
//                  .append($('<span class="text-danger"/>').text(file.error));
//          }
//          if (index + 1 === data.files.length) {
//              data.context.find('button')
//                  .text('Upload')
//                  .prop('disabled', !!data.files.error);
//          }
//      }).on('fileuploadprogressall', function (e, data) {
//          var progress = parseInt(data.loaded / data.total * 100, 10);
//          $('#progress .progress-bar').css(
//              'width',
//              progress + '%'
//          );
//      }).on('fileuploaddone', function (e, data) {
//          $.each(data.result.files, function (index, file) {
//              if (file.url) {
//                  var link = $('<a>')
//                      .attr('target', '_blank')
//                      .prop('href', file.url);
//                  $(data.context.children()[index])
//                      .wrap(link);
//              } else if (file.error) {
//                  var error = $('<span class="text-danger"/>').text(file.error);
//                  $(data.context.children()[index])
//                      .append('<br>')
//                      .append(error);
//              }
//          });
//      }).on('fileuploadfail', function (e, data) {
//          $.each(data.files, function (index, file) {
//              var error = $('<span class="text-danger"/>').text('File upload failed.');
//              $(data.context.children()[index])
//                  .append('<br>')
//                  .append(error);
//          });
//      }).prop('disabled', !$.support.fileInput)
//          .parent().addClass($.support.fileInput ? undefined : 'disabled');
//  });

  $('#fileupload').fileupload();
  
  function addFile(data) {
    $('#fileupload').fileupload('add', {
      url : "/taskstodo/files/api/create/"+taskModel.selectedTask().idAsString,
      files: files
    })
    .success(function (result, textStatus, jqXHR) {
      loadFiles();
    })
    .error(function (jqXHR, textStatus, errorThrown) {
      if (console && console.error) {
        console.error("Error uploading file! Error: " + errorThrown);
      }
    })
    .complete(function (result, textStatus, jqXHR) {/* ... */});
  }
  
  
  /////////////////////////////////////////////////////////////////////////////
  // GENERAL                                                                 //
  /////////////////////////////////////////////////////////////////////////////
  
  function hideElement(id) {
    $(id).hide();
  }

  function showElement(id) {
    $(id).show();
  }
  
  function toggleElement(id) {
    $(id).toggle();
  }
});
