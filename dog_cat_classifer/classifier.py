import numpy as np
import cv2

num_cats = 726
num_dogs = 783

train_X = []
train_T = []
test_X = []
test_T = []
for i in range(num_cats):
    if i < 600:
        img = cv2.imread("training_cats/cat{}.jpg".format(i+1), cv2.IMREAD_GRAYSCALE)
        if img is None or img.shape != (225, 225):
            continue
        img = np.reshape(img, img.shape[0]*img.shape[1])/255
        train_X.append(img)
        train_T.append(1)
    else:
        img = cv2.imread("training_cats/cat{}.jpg".format(i+1), cv2.IMREAD_GRAYSCALE)
        if img is None or img.shape != (225, 225):
            continue
        img = np.reshape(img, img.shape[0]*img.shape[1])/255
        test_X.append(img)
        test_T.append(1)

for i in range(num_dogs):
    if i < 600:
        img = cv2.imread("training_dogs/dog{}.jpg".format(i+1), cv2.IMREAD_GRAYSCALE)
        if img is None or img.shape != (225, 225):
            continue
        img = np.reshape(img, img.shape[0]*img.shape[1])/255
        train_X.append(img)
        train_T.append(0)
    else:
        img = cv2.imread("training_dogs/dog{}.jpg".format(i+1), cv2.IMREAD_GRAYSCALE)
        if img is None or img.shape != (225, 225):
            continue
        img = np.reshape(img, img.shape[0]*img.shape[1])/255
        test_X.append(img)
        test_T.append(0)
 
train_X = np.asarray(train_X)
train_T = np.asarray(train_T)
test_X = np.asarray(test_X)
test_T = np.asarray(test_T)

def cost(y, t):
    return np.mean(-t*np.log(y) - (1-t)*np.log(1-y))

def sigmoid(z):
    return 1 / (1 + np.exp(-z))

def pred(X, w, b):
    ret = np.matmul(X, w) + b
    return np.apply_along_axis(sigmoid, 0, ret)

def derivative_cost(X, w, b, t):
    y = pred(X, w, b)
    n = y.shape[0]
    dEdw = 1/n*np.matmul(X.T, y - t)
    dEdb = np.mean(y - t)
    return dEdw, dEdb

def get_accuracy(y, t):
    N = y.shape[0]
    count = 0
    for i in range(N):
        if (t[i] == 1 and y[i] >= 0.5) or (t[i] == 0 and y[i]) < 0.5:
            count += 1
    return count/N

def run_gradient_descent(X, T, num_iterations=100, alpha = 0.00025, batch_size = 100):
    w = np.ones(X.shape[1])*0.0001
    b = 0
    for i in range(num_iterations):
        perm = np.random.permutation(X.shape[0])
        T = T[perm]
        X = X[perm]
        for j in range(0, X.shape[0], batch_size):
            if j + batch_size >= X.shape[0]:
                break
            dEdw, dEdb = derivative_cost(X[j: j + batch_size], w, b, T[j: j + batch_size])
            w = w - alpha*dEdw
            b = b - alpha*dEdb
            print("iteration: {}, w: {}, b: {}".format(i,w,b))
    return w,b

w, b = run_gradient_descent(train_X, train_T)
y = pred(test_X, w, b)
print("\n{} pictures of cats and dogs were used for training...".format(train_X.shape[0]))
print("The program then looked at {} pictures it hadn't seen before".format(test_X.shape[0]))
print("and correctly classified {}% of them".format(round(get_accuracy(y, test_T), 2)*100))
