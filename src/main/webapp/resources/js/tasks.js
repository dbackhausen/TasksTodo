$(document).ready(function() {
  
  ko.punches.enableAll();
  
  // Initially load all tasks
  loadTasks();
  
  $(function() {
    $('#nestable-task-list').nestable({ 
      callback: function(l,e) {
        // l is the main container
        // e is the element that was moved
        console.log($(e).attr("id"));
        console.log($(e).parent().parent().attr("id"));
        
      }
    });
  });
  
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
      
      $('#task-details-tags').tagsinput();
      $('#task-details-tags').tagsinput('add', "Technology");
      $('#task-details-tags').tagsinput('add', "Information");
      $('#task-details-tags').tagsinput('add', "Information Retrieval");
      $('#task-details-tags').tagsinput('add', "Search");
      $('#task-details-tags').tagsinput('add', "Personalization");
      $('#task-details-tags').tagsinput('add', "Context");
    };
    
//    self.unselectTask = function() {
//      self.selectedTask = ko.observable();
//    };
    
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
      $('#details .inline-view').toggle();
      $('#details .inline-edit').toggle();
    };

    self.cancelEditTask = function(data) {
      $('#details .inline-view').toggle();
      $('#details .inline-edit').toggle();
    };
    
    self.saveTask = function() {
      saveTask(self.selectedTask());
    };
    
    self.deleteTask = function() {
      deleteTask(self.selectedTask());
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
    var parent = "52950ebe3004676c2b42a108"; // TODO
    var taskTitle = $("#input-new-task-title").val();

    if (taskTitle != null && taskTitle.trim().length > 0) {
      var json = { "title" : taskTitle };
      
      $.ajax({
        url: "/taskstodo/tasks/api/create/" + parent,
        data: JSON.stringify(json),
        type: "POST",
        
        beforeSend: function(xhr) {
          xhr.setRequestHeader("Accept", "application/json");
          xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function(data) {
          if (data != null) {
            taskModel.tasks.push(data);
          }
          
          // Reset the form
          $('#input-new-task-title').val(null);
          $('#new-task-button').toggle();
          $('#new-task-form').toggle();
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
      var json = { 
          "title" : task.title, 
          "description" : task.description,
          "created": task.created,
          "modified": task.modified,
          "dueDate": task.dueDate,
          "completedDate": task.completedDate,
          "reminderDate": task.reminderDate,
          "urgency": task.urgency,
          "priority": task.priority
      };

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
          
          // Reload all tasks
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
    if (task != null) {
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
          
          // Reload all tasks
          loadTasks();
        },
        error: function(jqXHR, textStatus, errorThrown) {
          if (console && console.error) {
            console.error("Error deleting task " + task.idAsString + "! Error: " + errorThrown);
          }
        }
      });
    }
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
      var json = { "body" : note };
      
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
  
//  $(function() {
//    'use strict';
//    $('#fileupload').fileupload({
//      url: '/taskstodo/files/api/create/52950f0730044756dfb4ee67',
//      dataType: 'json',
//      success: function (e, data) {
//        loadFiles();
//      },
//      error: function(jqXHR, textStatus, errorThrown) {
//        if (console && console.error) {
//          console.error("Error loading files! Error: " + errorThrown);
//        }
//      }
//    });
//  });
  
  function addFile() {
    var formData = new FormData($('#task-file-form')[0]);

    $.ajax({
        url: '/taskstodo/files/api/create/'+taskModel.selectedTask().idAsString,
        data: formData,
        type: 'POST',
        beforeSend: function(xhr) {
        },
        success: function(e, data) {
          loadFiles();
        },
        error: function(jqXHR, textStatus, errorThrown) {
          if (console && console.error) {
            console.error("Error loading files! Error: " + errorThrown);
          }
        },
        cache: false,
        contentType: false,
        processData: false
    });
  }
  
  /////////////////////////////////////////////////////////////////////////////
  // FILTERS                                                                 //
  /////////////////////////////////////////////////////////////////////////////
  
  ko.filters.smartdate = function(date) {
    if (date != null && date != undefined) {
      return moment(date).format("YYYY-MM-DD HH:mm");      
    } else {
      return "not specified";
    }
  };
  
  ko.filters.smarturgency = function(urgency) {
    if (urgency == 0) return "low";
    else if (urgency == 1) return "mid";
    else return "high";
  };
  
  ko.filters.smartpriority = function(priority) {
    if (priority == 0) return "low";
    else if (priority == 1) return "mid";
    else return "high";
  };
    
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
